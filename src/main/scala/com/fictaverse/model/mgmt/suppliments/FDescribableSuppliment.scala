package com.fictaverse.model.mgmt.suppliments

import com.fictaverse.model.traits.FDescribable

trait FDescribableSuppliment {

  def describable: FDescribable
  
  def updateDescribable(firstImpression: Option[String], description: Option[String]) {
    describable.firstImpression = firstImpression.getOrElse(describable.firstImpression)
    describable.description = description.getOrElse(describable.description)
  }
}