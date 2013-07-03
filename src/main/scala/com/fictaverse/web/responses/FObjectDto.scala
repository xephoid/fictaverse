package com.fictaverse.web.responses

import com.fictaverse.web.resources.FAssociationDto
import com.fictaverse.web.resources.FictaWebDto
import com.fictaverse.model.FWorld
import com.fictaverse.model.FStory
import java.util.ArrayList
import com.fictaverse.model.FTag
import com.fictaverse.util.FictaLogging
import com.fictaverse.model.FObject
import com.fictaverse.model.traits.FMultiNamed
import com.fictaverse.model.traits.FDescribable
import com.fictaverse.model.traits.FTaggable
import com.fictaverse.model.traits.FAssociable
import com.google.code.morphia.annotations.Entity
import scala.collection.JavaConversions._
import com.fictaverse.model.FLocation
import com.fictaverse.model.FCharacter
import com.fictaverse.model.FArtifact
import com.fictaverse.model.FAffiliation
import com.fictaverse.model.FEvent

case class FObjectDto(
    override val kind: String,
    id: String,
    worldId: String,
    name: String,
    description: String,
    firstImpression: String,
    aliases: List[String],
    associations: List[FAssociationDto],
    tags: List[String],

    /* Character specific */
    firstName: String = null,
    lastName: String = null
) extends FictaWebDto(kind) {
  
  def toWorld(w: Option[FWorld]) = {
    val world = w.getOrElse(new FWorld)
    world.name = name
    world.description = description
    world.firstImpression = firstImpression
    world
  }
  
  def toStory(s: Option[FStory]): FStory = {
    val story = s.getOrElse(new FStory)
    story.world = getWorld
    story.name = name
    setDescribabble(story) 
    setAliases(story)
    setTags(story)
    story
  }
  
  def toLocation(l: Option[FLocation]): FLocation = {
    val location = l.getOrElse(new FLocation)
    location.world = getWorld
    location.name = name
    setDescribabble(location)
    setAliases(location)
    setTags(location)
    location
  }
  
  def toCharacter(c: Option[FCharacter]): FCharacter = {
    val character = c.getOrElse(new FCharacter)
    character.world = getWorld
    character.firstName = firstName
    character.lastName = lastName
    setDescribabble(character)
    setAliases(character)
    setTags(character)
    character
  }
  
  def toArtifact(a: Option[FArtifact]): FArtifact = {
    val artifact = a.getOrElse(new FArtifact)
    artifact.world = getWorld
    artifact.name = name
    setDescribabble(artifact)
    setAliases(artifact)
    setTags(artifact)
    artifact
  }
  
  def toAffiliation(a: Option[FAffiliation]): FAffiliation = {
    val affiliation = a.getOrElse(new FAffiliation)
    affiliation.world = getWorld
    affiliation.name = name
    setDescribabble(affiliation)
    setAliases(affiliation)
    setTags(affiliation)
    affiliation
  }
  
  def toEvent(e: Option[FEvent]): FEvent = {
    val event = e.getOrElse(new FEvent)
    event.world = getWorld
    event.name = name
    setDescribabble(event)
    setAliases(event)
    setTags(event)
    event
  }
  
  private def getWorld: FWorld = {
    FWorld.findOneBy("externalId" -> worldId).getOrElse {
      throw new IllegalArgumentException("FObjectDto: Invalid world!")
    }
  }
  
  private def setDescribabble(describable: FDescribable) {
    describable.description = description
    describable.firstImpression = firstImpression
  }
  
  private def setAliases(aliased: FMultiNamed) {
    if (Option(aliases).isDefined) {
    	aliased.aliases = aliases.foldLeft(new ArrayList[String]) {
    		(list, alias) =>
    		list.add(alias)
    		list
    	}
    }
  }
  
  private def setTags(taggable: FTaggable) {
    if (Option(tags).isDefined) {
    	taggable.tags = tags.foldLeft(new ArrayList[FTag]) {
    		(list, tag) =>
    			val ftag = FTag.findOneBy("name" -> tag).getOrElse {
    			val newtag = new FTag
    			newtag.name = tag
    			// TODO: save!
    			newtag
    		}
    		list.add(ftag)
    		list
    	}
    }
  }
}

object FObjectDto extends FictaLogging {
  def apply(obj: FObject): FObjectDto = {
    val aliases = if (obj.isInstanceOf[FMultiNamed]) {
      obj.asInstanceOf[FMultiNamed].aliases.toList
    } else {
      List()
    }
    val (description, firstImpression) = if (obj.isInstanceOf[FDescribable]) {
      (obj.asInstanceOf[FDescribable].description, obj.asInstanceOf[FDescribable].firstImpression)
    } else {
      (null, null)
    }
    val tags = if (obj.isInstanceOf[FTaggable]) {
      obj.asInstanceOf[FTaggable].tags.map(_.name).toList
    } else {
      List()
    }
    val associations = if (obj.isInstanceOf[FAssociable]) {
      obj.asInstanceOf[FAssociable].associations.map(FAssociationDto(_)).toList
    } else {
      List()
    }
    
    val world = getFieldValueIfExists[FWorld]("world", obj)
    val worldId = if (world.isDefined) {
      world.get.externalId
    } else {
      null
    }
    
    val firstName = getFieldValueIfExists[String]("firstName", obj).getOrElse(null)
    val lastName = getFieldValueIfExists[String]("lastName", obj).getOrElse(null)
    
    val name = getFieldValueIfExists[String]("name", obj).getOrElse(firstName + " " + lastName)
    
    FObjectDto(obj.kind, obj.externalId, worldId, name, description, firstImpression, aliases, associations, tags, firstName, lastName)
  }
  
  private def getFieldValueIfExists[T](fieldName: String, obj: FObject): Option[T] = {
    if (obj.getClass.getDeclaredFields().exists(_.getName() == fieldName)) {
      val field = obj.getClass.getDeclaredField(fieldName)
      field.setAccessible(true)
      val result = field.get(obj).asInstanceOf[T]
      field.setAccessible(false)
      Option(result)
    } else {
      None
    }
  }
} 