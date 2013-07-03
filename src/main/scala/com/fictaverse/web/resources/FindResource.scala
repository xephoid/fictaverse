package com.fictaverse.web.resources

import com.fictaverse.web.FictaResource
import javax.ws.rs.Path
import javax.ws.rs.Produces
import javax.ws.rs.core.MediaType
import javax.ws.rs.QueryParam
import javax.ws.rs.GET
import com.yammer.metrics.annotation.Timed
import com.fictaverse.model.FWorld
import com.fictaverse.model.FObject
import com.fictaverse.model.dao.FictaDAO
import com.fictaverse.web.responses.FObjectDto
import com.fictaverse.model.FStory
import com.fictaverse.model.FLocation
import com.fictaverse.model.FCharacter
import com.fictaverse.model.FArtifact
import com.fictaverse.model.FAffiliation
import com.fictaverse.model.FEvent

@Path("/find")
@Produces(Array(MediaType.APPLICATION_JSON))
class FindResource extends FictaResource {

  @GET
  @Timed
  def find(
      @QueryParam("sessionId") sessionId: Option[String],
      @QueryParam("kind") kind: Option[String],
      @QueryParam("id") id: Option[String]): FictaWebDto = {
    require(kind.isDefined, "Kind is missing!")
    require(id.isDefined, "Id is missing!")
    val session = validateSession(sessionId)
    kind.get match {
      case "world" => doFind(id.get, FWorld)
      case "story" => doFind(id.get, FStory)
      case "location" => doFind(id.get, FLocation)
      case "character" => doFind(id.get, FCharacter)
      case "artifact" => doFind(id.get, FArtifact)
      case "affiliation" => doFind(id.get, FAffiliation)
      case "event" => doFind(id.get, FEvent)
      case _ => FErrorDto("Invalid kind!")
    }
  }
  
  private def doFind[T <: FObject](id: String, dao: FictaDAO[T]) = {
    FObjectDto(dao.findOneBy("externalId" -> id).getOrElse {
      throw new IllegalArgumentException("Object not found!")
    })
  }
}