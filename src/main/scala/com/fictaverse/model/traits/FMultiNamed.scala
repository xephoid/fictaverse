package com.fictaverse.model.traits

import java.util.{ List => JList }
import java.util.ArrayList

trait FMultiNamed {
  var aliases: JList[String] = new ArrayList[String]
}