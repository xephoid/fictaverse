package com.fictaverse.model

import java.util.{ List => JList }
import java.util.ArrayList
import com.google.code.morphia.annotations.Entity
import com.fictaverse.model.dao.FictaDAO
import com.google.code.morphia.annotations.Reference
import com.fictaverse.model.web.FSession
import java.util.Date

@Entity("user")
class FUser extends FObject {

  var email: String = null
  var encryptedPassword: Array[Byte] = null
  var lastLogin: Date = null
  var isAdmin: Boolean = false
  
  @Reference
  var worlds: JList[FWorld] = new ArrayList[FWorld]
  
  @Reference
  var session: FSession = null
}

object FUser extends FictaDAO[FUser]