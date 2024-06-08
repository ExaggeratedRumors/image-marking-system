package com.ertools.plugins

import com.ertools.commons.Utils
import com.ertools.domain.UserRepository
import io.ktor.server.application.*
import io.ktor.server.auth.*
import org.koin.ktor.ext.inject

/**
 * JWT token configuration
 */
fun Application.configureSecurity() {
    install(Authentication) {
        basic(Utils.AUTHENTICATION_USER) {
            realm = "Ktor Server"
            validate { credentials ->
                val userRepository by inject<UserRepository>()
                val user = userRepository.findByLogin(credentials.name)
                if (user != null && user.password == credentials.password) {
                    UserIdPrincipal(credentials.name)
                } else {
                    null
                }
            }
        }
    }
}