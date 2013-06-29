package com.fictaverse.web.resources

import com.fictaverse.model.FWorld
import com.fictaverse.model.web.FSession
import com.fictaverse.util.FictaJson
import com.fictaverse.model.FStory
import scala.collection.JavaConversions._
import com.fictaverse.model.FLocation
import com.fictaverse.model.FObject
import com.fictaverse.model.traits.FMultiNamed
import com.fictaverse.model.traits.FDescribable
import com.fictaverse.model.traits.FTaggable
import com.fictaverse.model.traits.FAssociable
import com.google.code.morphia.annotations.Entity
import com.fictaverse.model.FAssociation
import com.fictaverse.util.FictaLogging
import com.fictaverse.model.FTag
import java.util.ArrayList
import com.fictaverse.web.responses.FObjectDto

case class FictaWebDto(kind: String) {
  def toJson = FictaJson.toJson(this)
}

case class FAssociationDto(
    id: String,
    descriptor: String,
    target1: FObjectDto,
    target2: FObjectDto
) extends FictaWebDto("association")

object FAssociationDto {
  def apply(association: FAssociation): FAssociationDto = FAssociationDto(
      association.externalId,
      association.descrptor,
      FObjectDto(association.target1.asInstanceOf[FObject]),
      FObjectDto(association.target2.asInstanceOf[FObject])
  )
}

case class FSessionDto (
    id: String, 
    userId: String, 
    state: String, 
    world: FObjectDto
) extends FictaWebDto("session")

object FSessionDto {
  def apply(session: FSession): FSessionDto = FSessionDto(
      session.externalId,      session.user.externalId,      session.state.toString,      FObjectDto(session.world)
  )
}

case class FErrorDto (msg: String) extends FictaWebDto("error")
object FErrorDto {
  def apply(t: Throwable): FErrorDto = FErrorDto(t.getMessage())
}

case class FRedirectDto(url: String) extends FictaWebDto("redirect")