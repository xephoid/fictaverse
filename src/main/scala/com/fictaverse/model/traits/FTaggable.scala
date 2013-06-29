package com.fictaverse.model.traits

import com.fictaverse.model.FTag
import java.util.{ List => JList }
import java.util.ArrayList
import com.google.code.morphia.annotations.Reference
import org.bson.types.ObjectId

trait FTaggable {
  def id: ObjectId
  def name: String
  def description: String
  
  @Reference
  var tags: JList[FTag] = new ArrayList[FTag]
}