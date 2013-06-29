package com.fictaverse.model

import java.util.{ List => JList }
import com.fictaverse.model.traits.FDescribable
import java.util.ArrayList
import com.google.code.morphia.annotations.Reference
import com.google.code.morphia.annotations.Entity
import com.fictaverse.model.dao.FictaDAO

@Entity("world")
class FWorld extends FObject with FDescribable {

  var name: String = null
  
  @Reference
  var stories: JList[FStory] = new ArrayList[FStory]
  
}

object FWorld extends FictaDAO[FWorld]