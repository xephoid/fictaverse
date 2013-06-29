package com.fictaverse.model.mgmt.suppliments

import com.fictaverse.model.traits.FAssociable
import com.fictaverse.model.FAssociation

trait FAssociableSuppliment {

  def associable: FAssociable
  
  def updateAssociations(associations: Option[List[FAssociation]]) {
    if (associations.isDefined) {
      associable.associations.clear()
      associations.get.foreach(associable.associations.add(_))
    }
  }
}