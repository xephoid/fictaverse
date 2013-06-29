package com.fictaverse.model.mgmt

import com.fictaverse.model.FAssociation
import com.fictaverse.model.mgmt.suppliments.FDescribableSuppliment
import com.fictaverse.model.traits.FAssociable

trait FAssociationManagement extends FObjectManagement
with  FDescribableSuppliment {

  def association: FAssociation
  def obj = association
  def describable = association
  
  def saveOrUpdate(
      descriptor: Option[String] = None,
      firstImpression: Option[String] = None,
      description: Option[String] = None,
      target1: Option[FAssociable] = None,
      target2: Option[FAssociable] = None) {
    update()
    association.descrptor = descriptor.getOrElse(association.descrptor)
    association.target1 = target1.getOrElse(association.target1)
    association.target2 = target2.getOrElse(association.target2)
    updateDescribable(firstImpression, description)
    
    FAssociation.save(association)
  }
}