package com.ertools.plugins

import com.ertools.commons.Utils
import com.ertools.routes.authRoutes
import com.ertools.routes.imageRoutes
import com.ertools.routes.rootRoutes
import com.ertools.routes.userRoutes
import io.ktor.server.application.*
import io.ktor.server.plugins.swagger.*
import io.ktor.server.routing.*
import io.ktor.server.auth.*

fun Application.configureRouting() {
    routing {
        rootRoutes()
        authRoutes()
        swaggerUI(path = Utils.SWAGGER_PATH, swaggerFile = Utils.SWAGGER_FILE_PATH) {
            version = Utils.SWAGGER_VERSION
        }
        authenticate(Utils.JWT_NAME) {
            userRoutes()
            imageRoutes()
        }
    }
}
