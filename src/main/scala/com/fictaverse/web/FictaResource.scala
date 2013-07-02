package com.fictaverse.web

import com.fictaverse.util.FictaLogging
import javax.ws.rs.WebApplicationException
import javax.ws.rs.core.Response.Status
import com.fictaverse.web.resources.FErrorDto
import com.fictaverse.model.mgmt.FObjectTransactions
import com.fictaverse.model.web.FSession
import com.fictaverse.FSessionState

trait FictaResource extends FictaLogging with FObjectTransactions {

  def errorResponse(msg: String) = {
    error(msg)
    FErrorDto(msg)
  }
  
  def bail() {
    throw new WebApplicationException(Status.BAD_REQUEST)
  }
  
  def validateSession(sessionId: Option[String]) = {
    require(sessionId.isDefined, "Session is missing!")
    val session = FSession.findOneBy("externalId" -> sessionId.get, "state" -> FSessionState.active.toString).getOrElse {
      throw new IllegalArgumentException("Invalid session!")
    }
    require(FSession.isActive(session), "Session is expired!")
    session
  }
}