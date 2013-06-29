package com.fictaverse.model.mgmt

import com.fictaverse.model.mgmt.suppliments.FDescribableSuppliment
import com.fictaverse.model.mgmt.suppliments.FMultinamedSuppliment
import com.fictaverse.model.mgmt.suppliments.FTaggableSuppliment
import com.fictaverse.model.mgmt.suppliments.FAssociableSuppliment
import com.fictaverse.model.FLocation
import com.fictaverse.model.FTag
import com.fictaverse.model.FAssociation
import com.fictaverse.model.FWorld

trait FLocationManagement extends FObjectManagement
with FDescribableSuppliment
with FMultinamedSuppliment
with FTaggableSuppliment
with FAssociableSuppliment {

  def location: FLocation
  def obj = location
  def describable = location
  def multiNamed = location
  def taggable = location
  def associable = location
  
  def saveOrUpdate(
      world: Option[FWorld],
      name: Option[String],
      firstImpression: Option[String],
      description: Option[String],
      aliases: Option[List[String]],
      tags: Option[List[FTag]],
      associations: Option[List[FAssociation]]) {
    update()
    location.world = world.getOrElse(location.world)
    location.name = name.getOrElse(location.name)
    updateDescribable(firstImpression, description)
    updateAliases(aliases)
    updateTags(tags)
    updateAssociations(associations)
    
    FLocation.save(location)
  }
}