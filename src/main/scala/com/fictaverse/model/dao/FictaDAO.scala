package com.fictaverse.model.dao

import java.util.{ List => JList }
import scala.collection.JavaConversions._
import com.fictaverse.model.FObject
import org.bson.types.ObjectId
import java.util.ArrayList
import scala.actors.Futures
import com.google.code.morphia.logging.MorphiaLoggerFactory
import com.google.code.morphia.Morphia
import com.mongodb.Mongo
import com.mongodb.casbah.commons.conversions.scala.RegisterJodaTimeConversionHelpers
import com.fictaverse.model.FUser
import com.fictaverse.model.FWorld
import com.google.code.morphia.logging.slf4j.SLF4JLogrImplFactory
import com.fictaverse.util.FictaLogging
import com.fictaverse.model.FStory
import com.fictaverse.model.FTag
import com.fictaverse.model.FCharacter
import com.fictaverse.model.FLocation
import com.fictaverse.model.FArtifact
import com.fictaverse.model.FAssociation
import com.fictaverse.model.FAffiliation
import com.fictaverse.model.FAction
import com.fictaverse.model.FEvent
import com.fictaverse.FictaConfig
import com.fictaverse.model.web.FSession
import com.fictaverse.model.web.FMessage
import java.util.Date
import com.fictaverse.util.FictaIdGenerator

class FictaDAO[ObjectType <: FObject] (implicit m: Manifest[ObjectType]) extends FictaLogging {
	def find(id: String): Option[ObjectType] = findOneBy("_id" -> new ObjectId(id))

  def findBy(params: (String, Any)*): Option[JList[ObjectType]] = {
    debug("finding %s:%s" format(m.erasure.toString, params.mkString(",")))
    
    val query = params.foldLeft(FictaDAO.datastore.createQuery(m.erasure)) {
      (query, param) => query.filter(param._1, param._2)
    }
    
    if (query.get.isInstanceOf[ObjectType]) {
      Option(query.asList.asInstanceOf[JList[ObjectType]])
    } else {
      None
    }
  }
  
  def findOneBy(params: (String, Any)*): Option[ObjectType] = {
    val found = findBy(params: _*).getOrElse(new ArrayList)
    require(found.size() <= 1, "findOneBy found more than one object [%s] [%s]" format(m.erasure.getSimpleName, params.mkString(",")))
    found.headOption
  }
  
  protected[model] def save(obj: ObjectType): ObjectId = {
    obj.updatedAt = new Date
    if (!Option(obj.externalId).isDefined) {
      obj.externalId = FictaIdGenerator.generateId
    }
    val result = FictaDAO.datastore.save[ObjectType](obj)
    result.getId().asInstanceOf[ObjectId]
  }
}

object FictaDAO {
  RegisterJodaTimeConversionHelpers
  
  val mongo = new Mongo(FictaConfig.mongoHost, FictaConfig.mongoPort)
  MorphiaLoggerFactory.registerLogger(classOf[SLF4JLogrImplFactory])
  val morphia = new Morphia
  
  FictaSchema.classes.foreach(morphia.map(_))
  
  val datastore = morphia.createDatastore(mongo, FictaConfig.mongoDb)
}