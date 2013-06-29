package com.fictaverse.util

import com.google.gson.Gson

object FictaJson {

  private val gson = new Gson
  
  def toJson(x: Any): String = gson.toJson(x)
  
}