package com.fictaverse.model

import com.fictaverse.model.traits.FDescribable
import com.fictaverse.model.traits.FTaggable
import com.google.code.morphia.annotations.Entity
import com.fictaverse.model.dao.FictaDAO
import com.fictaverse.model.traits.FAssociable
import com.fictaverse.model.traits.FMultiNamed
import com.google.code.morphia.annotations.Reference
import java.util.{ List => JList }
import java.util.ArrayList

@Entity("story")
class FStory extends FObject
with FDescribable
with FTaggable
with FAssociable
with FMultiNamed {
  var name: String = null
  
  @Reference
  var world: FWorld = null
  
  @Reference
  var locations: JList[FLocation] = new ArrayList[FLocation]

  @Reference
  var characters: JList[FCharacter] = new ArrayList[FCharacter]
  
  @Reference
  var artifacts: JList[FArtifact] = new ArrayList[FArtifact]
  
  @Reference
  var affiliations: JList[FAffiliation] = new ArrayList[FAffiliation]

  @Reference
  var events: JList[FEvent] = new ArrayList[FEvent]
  
}

object FStory extends FictaDAO[FStory]