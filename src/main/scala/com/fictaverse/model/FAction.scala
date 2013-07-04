package com.fictaverse.model

import com.google.code.morphia.annotations.Entity
import com.fictaverse.model.dao.FictaDAO

@Entity("action")
class FAction extends FObject {

  var name: String = null
}

object FAction extends FictaDAO[FAction] {
  def getOrCreate(name: String): FAction = {
    FAction.findOneBy("name" -> name).getOrElse {
      val action = new FAction
      action.name = name
      FAction.save(action)
      action
    }
  }
}