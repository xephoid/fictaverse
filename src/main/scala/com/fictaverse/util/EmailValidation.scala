package com.fictaverse.util

import java.util.regex.Matcher
import java.util.regex.Pattern

object EmailValidation {
	val emailRegex = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$"
	val pattern = Pattern.compile(emailRegex)
	
	def validate(email: String) = pattern.matcher(email).matches()
}