package com.fictaverse.model

import com.google.code.morphia.annotations.Entity
import com.fictaverse.model.dao.FictaDAO

@Entity("action")
class FAction extends FObject {

  var name: String = null
}

object FAction extends FictaDAO[FAction]