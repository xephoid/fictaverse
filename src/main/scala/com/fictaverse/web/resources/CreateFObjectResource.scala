package com.fictaverse.web.resources

import com.fictaverse.web.FictaResource
import javax.ws.rs.Path
import javax.ws.rs.Produces
import javax.ws.rs.core.MediaType
import javax.ws.rs.QueryParam
import javax.ws.rs.GET
import com.yammer.metrics.annotation.Timed
import javax.ws.rs.Consumes
import com.fictaverse.web.responses.FObjectDto
import com.fictaverse.model.FWorld

@Path("/create")
@Produces(Array(MediaType.APPLICATION_JSON))
@Consumes(Array(MediaType.APPLICATION_JSON))
class CreateFObjectResource extends FictaResource {

  @GET
  @Timed
  def createObject(
      @QueryParam("sessionId") sessionId: Option[String],
      @QueryParam("kind") kind: Option[String],
      obj: FObjectDto) = {
    val session = validateSession(sessionId)
    require(kind.isDefined, "Kind is missing!")
    kind.get match {
      case "world" =>
        val world = obj.toWorld
        world.saveOrUpdate()
        session.world = world
        // TODO: Update save session
      case "story" =>
        val story = obj.toStory
        story.saveOrUpdate(world = Option(session.world))
      case "location" =>
        val location = obj.toLocation
        location.saveOrUpdate(world = Option(session.world))
      case "character" =>
        val character = obj.toCharacter
        character.saveOrUpdate(world = Option(session.world))
      case "artifact" =>
        //val artifact
      case _ => throw new IllegalArgumentException("Invalid kind!")
    }
  }
}