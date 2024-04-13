package com.ertools.plugins

import com.ertools.utils.Constants
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import java.io.File

fun Application.configureRouting() {
    routing {
        get("/{image}") {
            val filename = call.parameters["image"]
            println("ENGINE REQUEST: $filename")
            if(filename.isNullOrEmpty()) {
                call.respond(HttpStatusCode.BadRequest, "Missing image name")
            } else {
                val file = File("${Constants.IMAGES_PATH}/$filename")
                if(file.exists()) call.respondFile(file)
                else call.respond(HttpStatusCode.NotFound)
            }
        }
    }
}
