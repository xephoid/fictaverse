package com.fictaverse.web.resources

import com.fictaverse.web.FictaResource
import javax.ws.rs.Path
import javax.ws.rs.Produces
import javax.ws.rs.core.MediaType
import javax.ws.rs.QueryParam
import javax.ws.rs.GET
import com.yammer.metrics.annotation.Timed
import com.fictaverse.model.FUser
import com.fictaverse.model.mgmt.FObjectTransactions
import com.fictaverse.model.FWorld
import com.fictaverse.util.FictaJson
import com.fictaverse.web.responses.FObjectDto

@Path("/secret-signup")
@Produces(Array(MediaType.APPLICATION_JSON))
class SecretSignupResource extends FictaResource with FObjectTransactions {

  @GET
  @Timed
  def signup(
      @QueryParam("email") email: Option[String],
      @QueryParam("password") password: Option[String],
      @QueryParam("confirmPassword") confirmPassword: Option[String]): FictaWebDto = {
    try {
    	require(email.isDefined, "Email is required!")
    	require(password.isDefined && confirmPassword.isDefined, "Password and password confirm is required!")
    
    	val existing = FUser.findOneBy("email" -> email.get)
    	require(!existing.isDefined, "User with given email already exists!")
    
    	val user = new FUser
    	val world = new FWorld
    	world.saveOrUpdate(name = Option("Default World"))
    
    	debug("world[%s]: %s" format (world.id.toString(), FictaJson.toJson(FObjectDto(world))))
    
    	user.saveOrUpdate(email = email, password = password, confirmPassword = confirmPassword, worlds = Option(List(world)))
    	user.login()
    	FSessionDto(user.session)
    } catch {
      case t: IllegalArgumentException => 
         val resp = FErrorDto(t)  
        error(FictaJson.toJson(resp))
        resp
    }
  }
}