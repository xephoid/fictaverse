package com.fictaverse.web.resources

import com.fictaverse.web.FictaResource
import javax.ws.rs.Path
import javax.ws.rs.Produces
import javax.ws.rs.core.MediaType
import javax.ws.rs.QueryParam
import javax.ws.rs.GET
import com.yammer.metrics.annotation.Timed

@Path("/getKinds")
@Produces(Array(MediaType.APPLICATION_JSON))
class GetKindsResource extends FictaResource {
  
  @GET
  @Timed
  def getKinds(@QueryParam("sessionId") sessionId: Option[String]) = {
    validateSession(sessionId)
    KindsListDto(List(
        "Stories" -> "story",
        "Characters" -> "character",
        "Loctions" -> "location",
        "Artifacts" -> "artifact",
        "Affiliations" -> "affiliation",
        "Events" -> "event"))
  }
  
  case class KindsListDto(kinds: List[(String, String)]) extends FictaWebDto("kindList")
}