package com.fictaverse.model.mgmt

import com.fictaverse.model.FWorld
import com.fictaverse.model.FStory
import com.fictaverse.model.mgmt.suppliments.FDescribableSuppliment

trait FWorldManagement extends FObjectManagement
with FDescribableSuppliment {

  def world: FWorld
  def obj = world
  def describable = world
  
  def saveOrUpdate(
      name: Option[String] = None,
      firstImpression: Option[String] = None,
      description: Option[String] = None,
      stories: Option[List[FStory]] = None) {
    update()
    updateDescribable(firstImpression, description)
    world.name = name.getOrElse(world.name)
    if (stories.isDefined) {
      world.stories.clear()
      stories.get.foreach(world.stories.add(_))
    }
    FWorld.save(world)
  }
}