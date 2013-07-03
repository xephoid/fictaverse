package com.fictaverse.web.resources

import com.fictaverse.web.FictaResource
import javax.ws.rs.Path
import javax.ws.rs.Produces
import javax.ws.rs.core.MediaType
import javax.ws.rs.QueryParam
import javax.ws.rs.POST
import com.yammer.metrics.annotation.Timed
import javax.ws.rs.Consumes
import com.fictaverse.web.responses.FObjectDto
import com.fictaverse.model.FWorld
import com.fictaverse.model.FStory
import com.fictaverse.model.FLocation
import com.fictaverse.model.FCharacter
import com.fictaverse.model.FArtifact
import com.fictaverse.model.FAffiliation
import com.fictaverse.model.FEvent

@Path("/create")
@Produces(Array(MediaType.APPLICATION_JSON))
@Consumes(Array(MediaType.APPLICATION_JSON))
class CreateFObjectResource extends FictaResource {

  @POST
  @Timed
  def createObject(
      @QueryParam("sessionId") sessionId: Option[String],
      @QueryParam("kind") kind: Option[String],
      @QueryParam("id") id: Option[String],
      obj: FObjectDto) = {
    val session = validateSession(sessionId)
    require(kind.isDefined, "Kind is missing!")
    require(Option(obj).isDefined, "Did not recieve any data! body: %s" format(obj))
    kind.get match {
      case "world" =>
        val world = obj.toWorld( id match {
          case Some(externalId) => FWorld.findOneBy("externalId" -> externalId)
          case None => None
        })
        world.saveOrUpdate()
        session.world = world
        session.save()
      case "story" =>
        val story = obj.toStory( 
            id match {
            	case Some(externalId) => FStory.findOneBy("externalId" -> externalId)
            	case None => None
            }
        )
        story.saveOrUpdate(world = Option(session.world))
      case "location" =>
        val location = obj.toLocation(
            id match {
            	case Some(externalId) => FLocation.findOneBy("externalId" -> externalId)
            	case None => None
            }
        )
        location.saveOrUpdate(world = Option(session.world))
      case "character" =>
        val character = obj.toCharacter(
            id match {
            	case Some(externalId) => FCharacter.findOneBy("externalId" -> externalId)
            	case None => None
            }
        )
        character.saveOrUpdate(world = Option(session.world))
      case "artifact" =>
        val artifact = obj.toArtifact(
            id match {
            	case Some(externalId) => FArtifact.findOneBy("externalId" -> externalId)
            	case None => None
            }
        )
        artifact.saveOrUpdate(world = Option(session.world))
      case "affiliation" =>
        val affiliation = obj.toAffiliation(
            id match {
            	case Some(externalId) => FAffiliation.findOneBy("externalId" -> externalId)
            	case None => None
            }
        )
        affiliation.saveOrUpdate(world = Option(session.world))
      case "event" =>
        val event = obj.toEvent(
            id match {
            	case Some(externalId) => FEvent.findOneBy("externalId" -> externalId)
            	case None => None
            }
        )
        event.saveOrUpdate(world = Option(session.world))
      case _ => throw new IllegalArgumentException("Invalid kind!")
    }
    
    SuccessDto()
  }
  
  case class SuccessDto extends FictaWebDto("success")
}