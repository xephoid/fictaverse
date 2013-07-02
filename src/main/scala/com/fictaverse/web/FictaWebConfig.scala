package com.fictaverse.web

import com.yammer.dropwizard.config.Configuration
import org.hibernate.validator.constraints.NotEmpty
import com.fasterxml.jackson.annotation.JsonProperty

class FictaWebConfig extends Configuration {

  @NotEmpty
  @JsonProperty
  var mongoHost: String = "localhost"
  
  @NotEmpty
  @JsonProperty
  var mongoPort: String = "27017"
  
  @NotEmpty
  @JsonProperty
  var mongoDb: String = "fictaverse"
  
  @NotEmpty
  @JsonProperty
  var aesKey: String = "42f33ef1c821ddf8d9ef9314f0497f4b"
}