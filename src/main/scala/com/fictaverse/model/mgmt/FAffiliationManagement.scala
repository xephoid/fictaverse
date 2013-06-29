package com.fictaverse.model.mgmt

import com.fictaverse.model.FAffiliation
import com.fictaverse.model.mgmt.suppliments.FDescribableSuppliment
import com.fictaverse.model.mgmt.suppliments.FMultinamedSuppliment
import com.fictaverse.model.mgmt.suppliments.FTaggableSuppliment
import com.fictaverse.model.FTag
import com.fictaverse.model.mgmt.suppliments.FAssociableSuppliment
import com.fictaverse.model.FAssociation
import com.fictaverse.model.FWorld

trait FAffiliationManagement extends FObjectManagement
with FDescribableSuppliment
with FMultinamedSuppliment
with FTaggableSuppliment
with FAssociableSuppliment {

  def affiliation: FAffiliation
  def obj = affiliation
  def describable = affiliation
  def multiNamed = affiliation
  def taggable = affiliation
  def associable = affiliation
  
  def saveOrUpdate(
      world: Option[FWorld] = None,
      name: Option[String] = None,
      firstImpression: Option[String] = None,
      description: Option[String] = None,
      aliases: Option[List[String]] = None,
      tags: Option[List[FTag]] = None,
      associations: Option[List[FAssociation]] = None) {
    update()
    affiliation.world = world.getOrElse(affiliation.world)
    affiliation.name = name.getOrElse(affiliation.name)
    updateDescribable(firstImpression, description)
    updateAliases(aliases)
    updateTags(tags)
    updateAssociations(associations)
    
    FAffiliation.save(affiliation)
  }
}