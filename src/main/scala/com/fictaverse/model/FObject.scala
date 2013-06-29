package com.fictaverse.model

import com.google.code.morphia.annotations.Id
import org.bson.types.ObjectId
import java.util.Date
import com.google.code.morphia.annotations.Entity

class FObject {

  @Id
  var id: ObjectId = null

  val version: Int = 0
  var createdAt: Date = new Date
  var updatedAt: Date = new Date
  var externalId: String = null
  
  def kind = if (this.getClass.isAnnotationPresent(classOf[Entity])) {
    	this.getClass.getAnnotation(classOf[Entity]).value
    } else {
    	throw new IllegalArgumentException("Could not determin kind of data object!")
    }
}