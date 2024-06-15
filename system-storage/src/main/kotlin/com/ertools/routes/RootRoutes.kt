package com.ertools.routes

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.rootRoutes() {
    route("/") {
        get {
            call.respond(
                status = HttpStatusCode.OK,
                message = "Hello! This is SYSTEM-STORAGE service with Private API.")
        }
    }
}