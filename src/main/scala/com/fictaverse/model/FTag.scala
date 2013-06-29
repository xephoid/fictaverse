package com.fictaverse.model

import java.util.{ List => JList }
import com.google.code.morphia.annotations.Reference
import com.fictaverse.model.traits.FTaggable
import java.util.ArrayList
import com.fictaverse.model.traits.FTaggable
import com.google.code.morphia.annotations.Entity
import com.fictaverse.model.dao.FictaDAO

@Entity("tag")
class FTag extends FObject {

  var name: String = null
  
  @Reference
  var tagged: JList[FTaggable] = new ArrayList[FTaggable]
}

object FTag extends FictaDAO[FTag]