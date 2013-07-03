package com.fictaverse.model.web

import com.fictaverse.model.FObject
import com.fictaverse.model.FUser
import com.fictaverse.FSessionState
import com.google.code.morphia.annotations.Reference
import com.fictaverse.model.dao.FictaDAO
import com.fictaverse.model.FWorld
import com.google.code.morphia.annotations.Entity
import java.util.Date

@Entity("session")
class FSession extends FObject {

  var state: FSessionState = FSessionState.active
  var lastActivity: Date = new Date
  var activeKind: String = "character"
  
  @Reference
  var user: FUser = null
  
  @Reference
  var world: FWorld = null
  
  def save() {
    FSession.save(this)
  }
}

object FSession extends FictaDAO[FSession] {
  def isActive(session: FSession): Boolean = FSessionState.active.eq(session.state) || FSessionState.idle.eq(session.state)
}