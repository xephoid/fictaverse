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

case class FObjectDto(
    override val kind: String,
    id: String,
    worldId: String,
    name: String,
    description: String,
    firstImpression: String,
    aliases: List[String],
    associations: List[FAssociationDto],
    tags: List[String]
) extends FictaWebDto(kind) {
  
  def toWorld = {
    val world = new FWorld
    world.name = name
    world.description = description
    world.firstImpression = firstImpression
    world
  }
  
  def toStory: FStory = {
    val story = new FStory
    story.world = getWorld
    story.name = name
    setDescribabble(story) 
    setAliases(story)
    setTags(story)
    story
  }
  
  def toLocation: FLocation = {
    val location = new FLocation
    location.world = getWorld
    location.name = name
    setDescribabble(location)
    setAliases(location)
    setTags(location)
    location
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
    aliased.aliases = aliases.foldLeft(new ArrayList[String]) {
      (list, alias) =>
        list.add(alias)
        list
    }
  }
  
  private def setTags(taggable: FTaggable) {
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
    
    val name = getFieldValueIfExists[String]("name", obj).getOrElse(null)
    
    val world = getFieldValueIfExists[FWorld]("world", obj)
    val worldId = if (world.isDefined) {
      world.get.externalId
    } else {
      null
    }
    
    FObjectDto(obj.kind, obj.externalId, worldId, name, description, firstImpression, aliases, associations, tags)
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