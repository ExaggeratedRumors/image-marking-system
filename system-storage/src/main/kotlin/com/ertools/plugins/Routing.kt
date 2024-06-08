package com.ertools.plugins

import com.ertools.commons.Utils
import com.ertools.routes.userRoutes
import io.ktor.server.application.*
import io.ktor.server.plugins.swagger.*
import io.ktor.server.routing.*

fun Application.configureRouting() {
    routing {
        swaggerUI(path = "swagger-ui", swaggerFile = "openapi/documentation.yaml") {
            version = Utils.SWAGGER_VERSION
        }
        userRoutes()
    }
}
