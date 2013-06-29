package com.fictaverse.util

import org.slf4j.LoggerFactory

trait FictaLogging {

  private val logger = LoggerFactory.getLogger(this.getClass())
  
  def isErrorEnabled: Boolean = logger.isErrorEnabled
  
  def isTraceEnabled: Boolean = logger.isTraceEnabled

  def isDebugEnabled: Boolean = logger.isDebugEnabled

  def isInfoEnabled: Boolean = logger.isInfoEnabled
  
  def isWarningEnabled: Boolean = logger.isWarnEnabled

  def formatMessage(message: String): String = message

  def debug(message: => String) {
    //if (FictaLogging.isCapturing) FictaLogging.saveMessage(message)
    if (logger.isDebugEnabled) logger.debug(formatMessage(message.toString))
  }

  def debug(message: => String, ex: Throwable) {
    //if (FictaLogging.isCapturing) FictaLogging.saveMessage(message)
    if (logger.isDebugEnabled) logger.debug(formatMessage(message), ex)
  }

  def info(message: => String) {
    //if (FictaLogging.isCapturing) FictaLogging.saveMessage(message)
    if (logger.isInfoEnabled) logger.info(formatMessage(message))
  }

  def info(message: => String, ex: Throwable) {
    //if (FictaLogging.isCapturing) FictaLogging.saveMessage(message)
    if (logger.isInfoEnabled) logger.info(formatMessage(message), ex)
  }

  def warn(message: => String) {
    //if (FictaLogging.isCapturing) FictaLogging.saveMessage(message)
    if (logger.isWarnEnabled) logger.warn(formatMessage(message))
  }

  def warn(message: => String, ex: Throwable) {
    //if (FictaLogging.isCapturing) FictaLogging.saveMessage(message)
    if (logger.isWarnEnabled) logger.warn(formatMessage(message), ex)
  }

  def error(ex: Throwable) {
    //if (FictaLogging.isCapturing) FictaLogging.saveMessage(ex)
    if (logger.isErrorEnabled) logger.error(formatMessage(ex.getMessage), ex)
  }

  def error(message: => String) {
    //if (FictaLogging.isCapturing) FictaLogging.saveMessage(message)
    if (logger.isErrorEnabled) logger.error(formatMessage(message))
  }

  def error(message: => String, ex: Throwable) {
    //if (FictaLogging.isCapturing) FictaLogging.saveMessage(message)
    if (logger.isErrorEnabled) logger.error(formatMessage(message), ex)
  }

  def trace(message: => String) {
    //if (FictaLogging.isCapturing) FictaLogging.saveMessage(message)
    if (logger.isTraceEnabled) logger.trace(formatMessage(message))
  }

  def trace(message: => String, ex: Throwable) {
    //if (FictaLogging.isCapturing) FictaLogging.saveMessage(message)
    if (logger.isTraceEnabled) logger.trace(formatMessage(message), ex)
  }
}

object FictaLogger extends FictaLogging