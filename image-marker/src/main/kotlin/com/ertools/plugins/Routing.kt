package com.ertools.plugins

import com.ertools.utils.Constants
import com.ertools.utils.Resources
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.http.content.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import java.io.File

fun Application.configureRouting() {
    routing {
        staticResources("/images", "images") {
            enableAutoHeadResponse()
        }

        get("/") {
            call.respond("This is image-marker server.")
        }

        get("/config") {
            call.respond("Size: ${Resources.keySize}\n" +
                    "Length: ${Resources.keyLength}\n" +
                    "Opacity: ${Resources.keyOpacity}\n")
        }

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
