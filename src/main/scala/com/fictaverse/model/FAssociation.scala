package com.fictaverse.model

import com.fictaverse.model.traits.FDescribable
import com.fictaverse.model.traits.FAssociable
import com.fictaverse.model.traits.FAssociable
import com.google.code.morphia.annotations.Reference
import com.google.code.morphia.annotations.Entity
import com.fictaverse.model.dao.FictaDAO

@Entity("association")
class FAssociation extends FObject with FDescribable {

  var descrptor: String = null
  
  @Reference
  var target1: FAssociable = null
  
  @Reference
  var target2: FAssociable = null
}

object FAssociation extends FictaDAO[FAssociation]