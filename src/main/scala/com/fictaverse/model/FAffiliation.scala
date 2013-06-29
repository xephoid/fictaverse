package com.fictaverse.model

import com.fictaverse.model.traits.FDescribable
import com.fictaverse.model.traits.FMultiNamed
import com.fictaverse.model.traits.FTaggable
import com.fictaverse.model.traits.FAssociable
import com.google.code.morphia.annotations.Entity
import com.fictaverse.model.dao.FictaDAO
import com.fictaverse.model.traits.FActor
import com.google.code.morphia.annotations.Reference

@Entity("affiliation")
class FAffiliation extends FObject
with FDescribable
with FMultiNamed
with FTaggable
with FAssociable
with FActor {

  var name: String = null
  
  @Reference
  var world: FWorld = null
}

object FAffiliation extends FictaDAO[FAffiliation]