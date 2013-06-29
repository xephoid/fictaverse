package com.fictaverse

import com.fictaverse.web.FictaWebConfig

object FictaConfig {

  var webconfig: FictaWebConfig = null
  
  def mongoHost = webconfig.mongoHost
  def mongoPort = webconfig.mongoPort.toInt
  def mongoDb = webconfig.mongoDb
  def aesKey = webconfig.aesKey
  
}