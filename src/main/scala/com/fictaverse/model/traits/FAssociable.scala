package com.fictaverse.model.traits

import com.fictaverse.model.FAssociation
import java.util.{ List => JList }
import java.util.ArrayList
import com.google.code.morphia.annotations.Reference

trait FAssociable {
  
  @Reference
  var associations: JList[FAssociation] = new ArrayList[FAssociation]
}