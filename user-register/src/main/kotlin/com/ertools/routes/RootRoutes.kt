package com.ertools.routes

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlin.text.get

fun Route.rootRoutes() {
    route("/") {
        get {
            call.respond(
                status = HttpStatusCode.OK,
                message = "Hello! This is USER-REGISTER service with Public API."
            )
        }
    }
}