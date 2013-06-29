package com.fictaverse.model.mgmt

import com.fictaverse.model.FAction
import com.fictaverse.model.FEvent
import com.fictaverse.model.FAffiliation
import com.fictaverse.model.FArtifact
import com.fictaverse.model.FAssociation
import com.fictaverse.model.FCharacter
import com.fictaverse.model.FLocation
import com.fictaverse.model.FStory
import com.fictaverse.model.FTag
import com.fictaverse.model.FUser
import com.fictaverse.model.FWorld

trait FObjectTransactions {

  implicit def world2mgmt(target: FWorld): FWorldManagement = new FWorldManagement {
    def world = target
  }
  
  implicit def user2mgmt(target: FUser): FUserManagement = new FUserManagement {
    def user = target
  }
  
  implicit def tag2mgmt(target: FTag): FTagManagement = new FTagManagement {
    def tag = target
  }
  
  implicit def story2mgmt(target: FStory): FStoryManagement = new FStoryManagement {
    def story = target
  }
  
  implicit def location2mgmt(target: FLocation): FLocationManagement = new FLocationManagement {
    def location = target
  }
  
  implicit def character2mgmt(target: FCharacter): FCharacterManagment = new FCharacterManagment {
    def character = target
  }
  
  implicit def association2mgmt(target: FAssociation): FAssociationManagement = new FAssociationManagement {
    def association = target
  }
  
  implicit def artifact2mgmt(target: FArtifact): FArtifactManagement = new FArtifactManagement {
    def artifact = target 
  }
  
  implicit def affiliation2mgmt(target: FAffiliation): FAffiliationManagement = new FAffiliationManagement {
    def affiliation = target
  }
  
  implicit def event2mgmt(target: FEvent): FEventManagement = new FEventManagement {
    def event = target
  }
  
  implicit def action2mgmt(target: FAction): FActionManagement = new FActionManagement {
    def action = target
  }
}