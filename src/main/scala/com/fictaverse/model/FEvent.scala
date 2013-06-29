package com.fictaverse.model

import java.util.{ List => JList }
import com.fictaverse.model.traits.FDescribable
import com.fictaverse.model.traits.FMultiNamed
import com.fictaverse.model.traits.FTaggable
import com.fictaverse.model.traits.FActor
import com.fictaverse.model.traits.FActor
import java.util.ArrayList
import com.google.code.morphia.annotations.Reference
import com.google.code.morphia.annotations.Entity
import com.fictaverse.model.dao.FictaDAO

@Entity("event")
class FEvent extends FObject
with FDescribable
with FMultiNamed
with FTaggable {

  var name: String = null
  
  var action: FAction = null
  
  var turn: Int = FEvent.NONE

  @Reference
  var actor: FActor = null
  
  @Reference
  var targets: JList[FActor] = new ArrayList[FActor]
  
  @Reference
  var world: FWorld = null
}

object FEvent extends FictaDAO[FEvent] {
  val NONE = -1
}