package com.fictaverse.model.mgmt

import com.fictaverse.model.FEvent
import com.fictaverse.model.FAction
import com.fictaverse.model.traits.FActor
import com.fictaverse.model.FWorld

trait FEventManagement extends FObjectManagement {

  def event: FEvent
  def obj = event
  
  def saveOrUpdate(
      world: Option[FWorld] = None,
      name: Option[String] = None,
      action: Option[FAction] = None,
      turn: Option[Int] = None,
      actor: Option[FActor] = None,
      targets: Option[List[FActor]] = None) {
    update()
    event.world = world.getOrElse(event.world)
    event.name = name.getOrElse(event.name)
    event.action = action.getOrElse(event.action)
    event.turn = turn.getOrElse(event.turn)
    event.actor = actor.getOrElse(event.actor)
    
    if (targets.isDefined) {
      event.targets.clear()
      targets.get.foreach(event.targets.add(_))
    }
    
    FEvent.save(event)
  }
}