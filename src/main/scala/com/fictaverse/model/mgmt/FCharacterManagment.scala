package com.fictaverse.model.mgmt

import com.fictaverse.model.mgmt.suppliments.FDescribableSuppliment
import com.fictaverse.model.mgmt.suppliments.FMultinamedSuppliment
import com.fictaverse.model.mgmt.suppliments.FTaggableSuppliment
import com.fictaverse.model.mgmt.suppliments.FAssociableSuppliment
import com.fictaverse.model.FCharacter
import com.fictaverse.model.FTag
import com.fictaverse.model.FAssociation
import com.fictaverse.model.FWorld

trait FCharacterManagment extends FObjectManagement
with FDescribableSuppliment
with FMultinamedSuppliment
with FTaggableSuppliment
with FAssociableSuppliment {

  def character: FCharacter
  def obj = character
  def describable = character
  def multiNamed = character
  def taggable = character
  def associable = character
  
  def saveOrUpdate(
      world: Option[FWorld] = None,
      firstName: Option[String] = None,
      middleName: Option[String] = None,
      lastName: Option[String] = None,
      firstImpression: Option[String] = None,
      description: Option[String] = None,
      aliases: Option[List[String]] = None,
      tags: Option[List[FTag]] = None,
      associations: Option[List[FAssociation]] = None) {
    update()
    character.world = world.getOrElse(character.world)
    character.firstName = firstName.getOrElse(character.firstName)
    updateDescribable(firstImpression, description)
    updateAliases(aliases)
    updateTags(tags)
    updateAssociations(associations)
    
    FCharacter.save(character)
  }
}