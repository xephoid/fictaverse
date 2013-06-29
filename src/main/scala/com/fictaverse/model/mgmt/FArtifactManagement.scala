package com.fictaverse.model.mgmt

import com.fictaverse.model.FArtifact
import com.fictaverse.model.mgmt.suppliments.FDescribableSuppliment
import com.fictaverse.model.mgmt.suppliments.FTaggableSuppliment
import com.fictaverse.model.mgmt.suppliments.FMultinamedSuppliment
import com.fictaverse.model.mgmt.suppliments.FAssociableSuppliment
import com.fictaverse.model.FTag
import com.fictaverse.model.FAssociation
import com.fictaverse.model.FWorld

trait FArtifactManagement extends FObjectManagement
with  FDescribableSuppliment 
with FTaggableSuppliment
with FMultinamedSuppliment
with FAssociableSuppliment {

  def artifact: FArtifact
  def obj = artifact
  def describable = artifact
  def multiNamed = artifact
  def associable = artifact
  def taggable = artifact
  
  def saveOrUpdate(
      world: Option[FWorld] = None,
      name: Option[String] = None,
      firstImpression: Option[String] = None,
      description: Option[String] = None,
      aliases: Option[List[String]] = None,
      tags: Option[List[FTag]] = None,
      associations: Option[List[FAssociation]] = None) {
    update()
    artifact.world = world.getOrElse(artifact.world)
    artifact.name = name.getOrElse(artifact.name)
    updateDescribable(firstImpression, description)
    updateAliases(aliases)
    updateTags(tags)
    updateAssociations(associations)
    
    FArtifact.save(artifact)
  }
}