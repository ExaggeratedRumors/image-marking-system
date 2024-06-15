package com.ertools.plugins

import com.ertools.routes.registerRoutes
import com.ertools.routes.rootRoutes
import io.ktor.server.application.*
import io.ktor.server.routing.*

fun Application.configureRouting() {

    routing {
        rootRoutes()
        registerRoutes()
    }
}
