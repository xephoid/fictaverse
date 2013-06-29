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

object FictaAES {
  private val password = FictaConfig.aesKey.toCharArray
  private val cipher = Cipher.getInstance("AES/CBC/PKCS5Padding")
  
  private val secretKey = init()
  
  def init() = {
    val salt = (new SecureRandom).engineGenerateSeed(8)
    val factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1")
    val spec = new PBEKeySpec(password, salt, 65536, 256)
    val tmp = factory.generateSecret(spec)
    
    val secret = new SecretKeySpec(tmp.getEncoded(), "AES")
    cipher.init(Cipher.ENCRYPT_MODE, secret)
    
    val iv = cipher.getParameters.getParameterSpec(classOf[IvParameterSpec]).getIV
    cipher.init(Cipher.DECRYPT_MODE, secret, new IvParameterSpec(iv))
    secret
  }
  
  def encrypt(toEncrypt: String): Array[Byte] = {
    cipher.init(Cipher.ENCRYPT_MODE, secretKey, new IvParameterSpec(cipher.getIV))
    cipher.doFinal(toEncrypt.getBytes("UTF-8"))
  }
  
  def decrypt(toDecrypt: Array[Byte]): String = {
    cipher.init(Cipher.DECRYPT_MODE, secretKey, new IvParameterSpec(cipher.getIV))
    new String(cipher.doFinal(toDecrypt), "UTF-8")
  }
}