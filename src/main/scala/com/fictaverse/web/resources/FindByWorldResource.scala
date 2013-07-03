package com.fictaverse.web.resources

import com.fictaverse.web.FictaResource
import javax.ws.rs.Path
import javax.ws.rs.Produces
import javax.ws.rs.core.MediaType
import javax.ws.rs.QueryParam
import javax.ws.rs.GET
import com.yammer.metrics.annotation.Timed
import com.fictaverse.model.FWorld
import com.fictaverse.model.FStory
import scala.collection.JavaConversions._
import java.util.ArrayList
import com.fictaverse.model.FLocation
import com.fictaverse.model.FCharacter
import com.fictaverse.model.FArtifact
import com.fictaverse.model.dao.FictaDAO
import com.fictaverse.model.FObject
import com.fictaverse.model.FAffiliation
import com.fictaverse.model.FEvent
import com.fictaverse.web.responses.FObjectDto

@Path("/findAllByWorld")
@Produces(Array(MediaType.APPLICATION_JSON))
class FindByWorldResource extends FictaResource {

  @GET
  @Timed
  def findAllByWorld(
      @QueryParam("sessionId") sessionId: Option[String],
      @QueryParam("kind") kind: Option[String]): List[FictaWebDto] = {
    require(kind.isDefined, "Kind is missing!")
    val session = validateSession(sessionId)
    val world = session.world
    session.activeKind = kind.get
    session.save()
    
    kind.get match {
      case "world" => List(FObjectDto(world))
      case "story" => doFind(world, FStory)
      case "location" => doFind(world, FLocation)
      case "character" => doFind(world, FCharacter)
      case "artifact" => doFind(world, FArtifact)
      case "affiliation" => doFind(world, FAffiliation)
      case "event" => doFind(world, FEvent)
      case _ => List(FErrorDto("Invalid kind!"))
    }
  }
  
  private def doFind[T <: FObject](world: FWorld, dao: FictaDAO[T]) = {
    dao.findBy("world" -> world).getOrElse(new ArrayList[T]).map(FObjectDto(_)).toList
  }
}