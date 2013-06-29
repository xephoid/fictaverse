package com.fictaverse.util

import java.security.SecureRandom
import java.math.BigInteger

object FictaIdGenerator {

  val random: SecureRandom = new SecureRandom
  
  def generateId = {
    new BigInteger(130, random).toString(32)
  }
}