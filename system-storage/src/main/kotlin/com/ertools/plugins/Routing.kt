package com.ertools.plugins

import com.ertools.commons.Utils
import com.ertools.routes.userRoutes
import io.ktor.server.application.*
import io.ktor.server.plugins.swagger.*
import io.ktor.server.routing.*

fun Application.configureRouting() {
    routing {
        swaggerUI(path = Utils.SWAGGER_PATH, swaggerFile = Utils.SWAGGER_FILE_PATH) {
            version = Utils.SWAGGER_VERSION
        }
        userRoutes()
    }
}
