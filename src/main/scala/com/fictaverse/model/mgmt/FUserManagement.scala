package com.fictaverse.model.mgmt

import com.fictaverse.model.FUser
import com.fictaverse.model.FWorld
import com.fictaverse.security.FictaAES
import com.fictaverse.util.EmailValidation
import com.fictaverse.FSessionState
import com.fictaverse.model.web.FSession
import scala.actors.Futures
import scala.collection.JavaConversions._
import java.util.Date

trait FUserManagement extends FObjectManagement {

  def user: FUser
  def obj = user
  
  def saveOrUpdate(
      email: Option[String] = None,
      worlds: Option[List[FWorld]] = None,
      password: Option[String] = None,
      confirmPassword: Option[String] = None) {
    update()
    
    if (email.isDefined) {
      require(EmailValidation.validate(email.get), "Email is not valid!")
      user.email = email.get
    }
    
    if (worlds.isDefined) {
      user.worlds.clear()
      worlds.get.foreach(user.worlds.add(_))
    }
    
    if (!Option(user.encryptedPassword).isDefined && password.isDefined) {
      require(confirmPassword.isDefined && confirmPassword.get == password.get, "Password[%s] and password confirmation [%s] do not match!" format(password.get, confirmPassword.get))
      user.encryptedPassword = FictaAES.encrypt(password.get)
    }
    
    FUser.save(user)
  }
  
  def login() {
    // If session exists and is active expire old session
    if (Option(user.session).isDefined) {
      if (FSession.isActive(user.session)) {
        Futures.future {
        	user.session.state = FSessionState.expired
        	FSession.save(user.session)
        }
      }
    }
    user.session = new FSession
    user.session.user = user
    user.session.world = user.worlds.sortWith((a, b) => a.updatedAt.after(b.updatedAt)).head
    user.session.state = FSessionState.active
    FSession.save(user.session)
    
    user.lastLogin = new Date
    FUser.save(user)
  }
  
  def logout() {
    require(Option(user.session).isDefined, "User is not logged in!")
    user.session.state = FSessionState.expired
    FSession.save(user.session)
    user.session = null
    FUser.save(user)
  }
  
  def changePassword(oldPassword: String, newPassword: String) {
    require(oldPassword.eq(FictaAES.decrypt(user.encryptedPassword)), "Old password does not match!")
    user.encryptedPassword = FictaAES.encrypt(newPassword)
    FUser.save(user)
  }
}