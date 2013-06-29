name := "fictaverse"

version := "0.1"

scalaVersion := "2.9.1"

resolvers ++= Seq(
	"Spring Maven MILESTONE Repository" at "http://maven.springframework.org/milestone",
	"Coda Hale repo" at "http://repo.codahale.com/"
)

libraryDependencies += "org.mongodb" % "casbah_2.9.2" % "2.4.1"

libraryDependencies ++= Seq(
  "com.yammer.dropwizard" % "dropwizard-core" % "0.6.1",
  "com.yammer.dropwizard" %% "dropwizard-scala" % "0.6.1",
  "javax.servlet" % "javax.servlet-api" % "3.0.1"
)

ivyXML :=
  <dependency org="org.eclipse.jetty.orbit" name="javax.servlet" rev="3.0.0.v201112011016">
    <artifact name="javax.servlet" type="orbit" ext="jar"/>
  </dependency>

libraryDependencies += "asm" % "asm-all" % "3.3.1" 

libraryDependencies += "com.google.code.gson" % "gson" % "1.7.1"

libraryDependencies += "org.scalaj" %% "scalaj-time" % "0.6"

libraryDependencies += "org.joda" % "joda-convert" % "1.1"

libraryDependencies += "net.minidev" % "json-smart" % "1.1.1"

libraryDependencies += "org.apache.commons" % "commons-email" % "1.2"

libraryDependencies += "redis.clients" % "jedis" % "2.1.0"
