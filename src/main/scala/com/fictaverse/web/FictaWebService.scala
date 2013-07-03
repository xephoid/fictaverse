package com.fictaverse.web

import com.yammer.dropwizard.ScalaService
import com.yammer.dropwizard.config.Bootstrap
import com.yammer.dropwizard.bundles.ScalaBundle
import com.yammer.dropwizard.config.Environment
import com.fictaverse.FictaConfig
import com.fictaverse.web.resources.SignInResource
import com.fictaverse.web.resources.SecretSignupResource
import com.fictaverse.web.resources.GetKindsResource
import com.fictaverse.web.resources.CreateFObjectResource
import com.fictaverse.web.resources.FindByWorldResource
import com.fictaverse.web.resources.FindResource

object FictaWebService extends ScalaService[FictaWebConfig] {
  def initialize(boot: Bootstrap[FictaWebConfig]) = {
    boot.setName("Fictaverse")
    boot.addBundle(new ScalaBundle)
  }
  
  def run(config: FictaWebConfig, env: Environment) = {
    FictaConfig.webconfig = config
    env.addResource(new SignInResource)
    env.addResource(new SecretSignupResource)
    env.addResource(new GetKindsResource)
    env.addResource(new CreateFObjectResource)
    env.addResource(new FindByWorldResource)
    env.addResource(new FindResource)
  }
}