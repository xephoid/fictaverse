package com.fictaverse.model.dao

import com.fictaverse.model.FObject
import com.fictaverse.model.FUser
import com.fictaverse.model.FWorld
import com.fictaverse.model.FStory
import com.fictaverse.model.FTag
import com.fictaverse.model.FLocation
import com.fictaverse.model.FCharacter
import com.fictaverse.model.FArtifact
import com.fictaverse.model.FAssociation
import com.fictaverse.model.FAffiliation
import com.fictaverse.model.FAction
import com.fictaverse.model.FEvent
import com.fictaverse.model.web.FSession
import com.fictaverse.model.web.FMessage

object FictaSchema {

  val classes: List[Class[_ <: FObject]] = List(
		  classOf[FUser],
		  classOf[FWorld],
		  classOf[FStory],
		  classOf[FTag],
		  classOf[FLocation],
		  classOf[FCharacter],
		  classOf[FArtifact],
		  classOf[FAssociation],
		  classOf[FAffiliation],
		  classOf[FAction],
		  classOf[FEvent],
  
		  classOf[FSession],
		  classOf[FMessage]      
      )
}