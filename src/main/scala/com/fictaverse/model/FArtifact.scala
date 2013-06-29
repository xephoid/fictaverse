package com.fictaverse.model

import com.fictaverse.model.traits.FDescribable
import com.fictaverse.model.traits.FTaggable
import com.fictaverse.model.traits.FMultiNamed
import com.google.code.morphia.annotations.Entity
import com.fictaverse.model.traits.FAssociable
import com.fictaverse.model.dao.FictaDAO
import com.fictaverse.model.traits.FActor
import com.google.code.morphia.annotations.Reference

@Entity("artifact")
class FArtifact extends FObject
with FDescribable
with FTaggable
with FMultiNamed
with FAssociable
with FActor {

  var name: String = null
  
  @Reference
  var world: FWorld = null
}

object FArtifact extends FictaDAO[FArtifact]