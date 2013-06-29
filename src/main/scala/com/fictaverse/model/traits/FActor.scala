package com.fictaverse.model.traits

import java.util.{ List => JList }
import java.util.ArrayList
import com.fictaverse.model.FAction

trait FActor {
  var canDo: JList[FAction] = new ArrayList[FAction]
}