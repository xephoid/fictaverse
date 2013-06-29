package com.fictaverse.model.mgmt.suppliments

import com.fictaverse.model.traits.FTaggable
import com.fictaverse.model.FTag

trait FTaggableSuppliment {

  def taggable: FTaggable
  
  def updateTags(tags: Option[List[FTag]]) {
    if (tags.isDefined) {
      taggable.tags.clear()
      tags.get.foreach(taggable.tags.add(_))
    }
  }
}