package com.fictaverse.model.mgmt

import com.fictaverse.model.FStory
import com.fictaverse.model.mgmt.suppliments.FDescribableSuppliment
import com.fictaverse.model.mgmt.suppliments.FMultinamedSuppliment
import com.fictaverse.model.mgmt.suppliments.FTaggableSuppliment
import com.fictaverse.model.mgmt.suppliments.FAssociableSuppliment
import com.fictaverse.model.FTag
import com.fictaverse.model.FAssociation
import com.fictaverse.model.FWorld
import com.fictaverse.model.FLocation
import com.fictaverse.model.FCharacter
import com.fictaverse.model.FArtifact
import com.fictaverse.model.FAffiliation
import com.fictaverse.model.FEvent

trait FStoryManagement extends FObjectManagement
with FDescribableSuppliment
with FMultinamedSuppliment
with FTaggableSuppliment
with FAssociableSuppliment {

  def story: FStory
  def obj = story
  def describable = story
  def multiNamed = story
  def taggable = story
  def associable = story
  
  def saveOrUpdate(
      world: Option[FWorld] = None,
      name: Option[String] = None,
      firstImpression: Option[String] = None,
      description: Option[String] = None,
      aliases: Option[List[String]] = None,
      tags: Option[List[FTag]] = None,
      associations: Option[List[FAssociation]] = None,
      locations: Option[List[FLocation]] = None,
      characters: Option[List[FCharacter]] = None,
      artifacts: Option[List[FArtifact]] = None,
      affiliations: Option[List[FAffiliation]] = None,
      events: Option[List[FEvent]] = None) {
    update()
    
    story.world = world.getOrElse(story.world)
    story.name = name.getOrElse(story.name)
    updateDescribable(firstImpression, description)
    updateAliases(aliases)
    updateTags(tags)
    updateAssociations(associations)
    
    if (locations.isDefined) {
      story.locations.clear()
      locations.get.foreach(story.locations.add(_))
    }
    
    if (characters.isDefined) {
      story.characters.clear()
      characters.get.foreach(story.characters.add(_))
    }
    
    if (artifacts.isDefined) {
      story.artifacts.clear()
      artifacts.get.foreach(story.artifacts.add(_))
    }
    
    if (affiliations.isDefined) {
      story.affiliations.clear()
      affiliations.get.foreach(story.affiliations.add(_))
    }
    
    if (events.isDefined) {
      story.events.clear()
      events.get.foreach(story.events.add(_))
    }
    
    FStory.save(story)
  }
}