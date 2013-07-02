package com.fictaverse.security

import javax.crypto.KeyGenerator
import javax.crypto.SecretKeyFactory
import javax.crypto.spec.PBEKeySpec
import javax.crypto.spec.SecretKeySpec
import javax.crypto.Cipher
import javax.crypto.spec.IvParameterSpec
import sun.security.provider.SecureRandom
import com.fictaverse.util.FictaIdGenerator
import com.fictaverse.FictaConfig
import scala.collection.mutable.ArrayBuffer

object FictaAES {
  private val ALGORITHM_KEY_TYPE 	= "AES"
  private val ALGORITHM 			= "AES/CBC/PKCS5Padding"
  private val AES_KEY_SIZE        	= 256
  
  private val password = FictaConfig.aesKey
  
  val random = FictaIdGenerator.random
  
  def getKey: Array[Byte] = password.getBytes
  
  // Get the IV (if preset, override this)
  def getIV: Array[Byte] = {
    generateIv()
  }
  
  /**
   * Generate a new random initialization vector
   * @return      A random byte array of size 16
   */
  def generateIv(): Array[Byte] = {
    val iv = new Array[Byte](16)
    random.nextBytes(iv)
    iv
  }
  
  /**
   * For convenience.
   */
  def encrypt(string: String): Array[Byte] = encrypt(string.getBytes)
  
  def encrypt(bytes: Array[Byte]): Array[Byte] = {
    Option(bytes) match {
      case None     => null
      case Some(b) if(b.length==0) => b
      case Some(b)  => {
        val key = getKey
        val iv = getIV
        val keySpec = new SecretKeySpec(key, ALGORITHM_KEY_TYPE)
        val ivSpec = new IvParameterSpec(iv)

        val cipher = Cipher.getInstance(ALGORITHM)
        cipher.init(Cipher.ENCRYPT_MODE, keySpec, ivSpec)

        val enc = cipher.doFinal(b)

        // Build the encrypted storage value
        val ret = new ArrayBuffer[Byte](enc.length+iv.length+1)
        ret += '\0'
        ret ++= iv
        ret ++= enc
        ret.toArray
      }
    }
  }

  def decrypt(bytes: Array[Byte]): String = {
    val decrypted = Option(bytes) match {
      case None => null
      case Some(b) if(b.length==0) => b
      case Some(b) => {
        val version = b(0)
        version match {
          case '\0' => {  // AES encryption with iv block

            val iv = new Array[Byte](16)
            Array.copy(b,1,iv,0,16)

            val encryptedValue = new Array[Byte](b.length-17)
            Array.copy(b,17,encryptedValue,0,b.length-17)

            val key: Array[Byte] = getKey

            val keySpec = new SecretKeySpec(key, ALGORITHM_KEY_TYPE)
            val ivSpec = new IvParameterSpec(iv)

            val cipher = Cipher.getInstance(ALGORITHM)
            cipher.init(Cipher.DECRYPT_MODE, keySpec, ivSpec)

            cipher.doFinal(encryptedValue)
          }
          case _ => {
            // If version is not 0, most likely an older encryption with fixed iv
            val keySpec = new SecretKeySpec(getKey, ALGORITHM_KEY_TYPE)
            val ivSpec = new IvParameterSpec(getIV)
            val cipher = Cipher.getInstance(ALGORITHM)
            cipher.init(Cipher.DECRYPT_MODE, keySpec, ivSpec)
            cipher.doFinal(b)
          }
        }
      }
    }
    new String(decrypted, "UTF-8")
  }
}