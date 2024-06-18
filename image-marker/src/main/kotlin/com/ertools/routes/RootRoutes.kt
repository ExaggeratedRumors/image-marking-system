package com.ertools.routes

import com.ertools.commons.Utils
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.rootRoutes() {
    get("/") {
        call.respond("Hello! This is IMAGE-MARKER service with Public API.")
    }

    get("/config") {
        call.respond("Size: ${Utils.KEY_SIZE}\n" + "Opacity: ${Utils.KEY_OPACITY}\n")
    }
}