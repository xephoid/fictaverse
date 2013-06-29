package com.fictaverse.model.mgmt

import com.fictaverse.model.FAction

trait FActionManagement extends FObjectManagement {

  def action: FAction
  def obj = action
  
  def saveOrUpdate(name: Option[String] = None) {
    update()
    action.name = name.getOrElse(action.name)
    FAction.save(action)
  }
}