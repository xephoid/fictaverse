package com.fictaverse.model.mgmt.suppliments

import com.fictaverse.model.traits.FMultiNamed

trait FMultinamedSuppliment {

  def multiNamed: FMultiNamed
  
  def updateAliases(aliases: Option[List[String]]) {
    if (aliases.isDefined) {
      multiNamed.aliases.clear()
      aliases.get.foreach(multiNamed.aliases.add(_))
    }
  }
}