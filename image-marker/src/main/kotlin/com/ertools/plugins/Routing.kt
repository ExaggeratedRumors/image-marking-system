package com.ertools.plugins

import com.ertools.routes.fetchRoutes
import com.ertools.routes.rootRoutes
import com.ertools.routes.uploadRoutes
import io.ktor.server.application.*
import io.ktor.server.http.content.*
import io.ktor.server.routing.*

fun Application.configureRouting() {
    routing {
        staticResources("/images", "images") {
            enableAutoHeadResponse()
        }
        rootRoutes()
        fetchRoutes()
        uploadRoutes()
    }
}
