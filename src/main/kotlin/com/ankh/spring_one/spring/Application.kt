package com.ankh.spring_one.spring

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@SpringBootApplication
class Application

fun main(args: Array<String>) {
	runApplication<Application>(*args)
}

@RestController
class MessageResource(){

	@GetMapping
	fun index(): List<Message> = listOf(
			Message("1","Hello"),
			Message("2","Hola")
	)
}

data class Message(val id: String, val text: String)