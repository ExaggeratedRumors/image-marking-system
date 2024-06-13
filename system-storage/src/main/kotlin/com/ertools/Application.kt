package com.ertools

import com.ertools.dto.entity.User
import com.ertools.plugins.*
import com.mongodb.client.model.Filters
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.bson.types.ObjectId

fun main(args: Array<String>): Unit = EngineMain.main(args)

fun Application.module() {
    configureDatabases()
    configureSerialization()
    //configureSecurity()
    configureRouting()
}