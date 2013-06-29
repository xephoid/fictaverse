package com.fictaverse.model.mgmt

import com.fictaverse.model.FTag
import com.fictaverse.model.traits.FTaggable

trait FTagManagement extends FObjectManagement {

  def tag: FTag
  def obj = tag
  
  def saveOrUpdate(
      name: Option[String] = None,
      tagged: Option[List[FTaggable]] = None) {
    update()
    tag.name = name.getOrElse(tag.name)
    if (tagged.isDefined) {
      tag.tagged.clear()
      tagged.get.foreach(tag.tagged.add(_))
    }
    
    FTag.save(tag)
  }
}