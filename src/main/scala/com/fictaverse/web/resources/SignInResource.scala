package com.fictaverse.web.resources

import javax.ws.rs.Path
import javax.ws.rs.Produces
import javax.ws.rs.core.MediaType
import javax.ws.rs.QueryParam
import javax.ws.rs.GET
import com.yammer.metrics.annotation.Timed
import com.fictaverse.model.FUser
import com.fictaverse.model.mgmt.FObjectTransactions
import com.fictaverse.security.FictaAES
import com.fictaverse.web.FictaResource
import com.fictaverse.util.FictaJson

@Path("/signin")
@Produces(Array(MediaType.APPLICATION_JSON))
class SignInResource extends FictaResource with FObjectTransactions {

  @GET
  @Timed
  def signIn(@QueryParam("email") email: Option[String],
      @QueryParam("password") password: Option[String]): FictaWebDto = {
    //info("Body: \n%s" format body)
    info("Email: %s\tPassword: %s"format(email, password))
    try {
    	require(email.isDefined && password.isDefined, "Email and password are required!")
    	val user = FUser.findOneBy("email" -> email.get)
    	require(user.isDefined, "Email / password combination do not match!")
    	if (FictaAES.decrypt(user.get.encryptedPassword) == password.get) {
    		user.get.login()
    		FSessionDto(user.get.session)
    	} else {
    		errorResponse("Email / password combination do not match!")
    	}
    } catch {
      case t: IllegalArgumentException =>
        val resp = FErrorDto(t)  
        error(FictaJson.toJson(resp))
        resp
    }
  }
}