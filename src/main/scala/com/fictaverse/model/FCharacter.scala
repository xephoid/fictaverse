package com.fictaverse.model

import com.google.code.morphia.annotations.Entity
import com.fictaverse.model.traits.FDescribable
import com.fictaverse.model.traits.FTaggable
import com.fictaverse.model.traits.FMultiNamed
import com.fictaverse.model.traits.FAssociable
import com.fictaverse.model.dao.FictaDAO
import com.fictaverse.model.traits.FActor
import com.google.code.morphia.annotations.Reference

@Entity("character")
class FCharacter extends FObject
with FDescribable
with FTaggable
with FMultiNamed 
with FAssociable
with FActor {

  val name = firstName + " " + lastName
  
  var firstName: String = null
  var middleName: String = null
  var lastName: String = null
  
  // TODO: Custom attributes
  @Reference
  var world: FWorld = null
}

object FCharacter extends FictaDAO[FCharacter]