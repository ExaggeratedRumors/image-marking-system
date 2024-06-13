package com.ertools.plugins


import com.ertools.commons.Utils
import com.ertools.domain.UserRepository
import io.ktor.server.application.*
/*
import io.ktor.server.auth.*
import org.koin.ktor.ext.inject
import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import io.ktor.auth.jwt.*
*/

/**
 * JWT token configuration
 */
fun Application.configureSecurity() {
/*    install(Authentication) {
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

        /*jwt("auth-jwt") {
            realm = "ktor sample app"
            verifier(
                JWT
                    .require(Algorithm.HMAC256("secret"))
                    .withAudience("ktor-audience")
                    .withIssuer("ktor-issuer")
                    .build()
            )
            validate { credential ->
                if (credential.payload.audience.contains("ktor-audience")) JWTPrincipal(credential.payload) else null
            }
        }*/
    }*/
}