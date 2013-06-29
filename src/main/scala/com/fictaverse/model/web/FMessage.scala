package com.fictaverse.model.web

import com.fictaverse.model.FObject
import com.google.code.morphia.annotations.Entity
import com.google.code.morphia.annotations.Reference
import com.fictaverse.FMessageState
import com.fictaverse.model.dao.FictaDAO

@Entity("message")
class FMessage extends FObject {

  @Reference
  var session: FSession = null
  
  var state: FMessageState = FMessageState.created
  var response: String = null
}

object FMessage extends FictaDAO[FMessage]