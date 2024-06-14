package com.ertools.plugins

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.ertools.commons.Utils
import com.ertools.domain.UserRepository
import io.ktor.server.application.*
import io.ktor.server.auth.*
import org.koin.ktor.ext.inject
import io.ktor.server.auth.jwt.*


/**
 * JWT token configuration
 */
fun Application.configureSecurity() {
    install(Authentication) {
        jwt(Utils.JWT_NAME) {
            realm = Utils.JWT_REALM
            verifier(
                JWT.require(Algorithm.HMAC256(Utils.JWT_SECRET))
                    .withAudience(Utils.JWT_AUDIENCE)
                    .withIssuer(Utils.JWT_ISSUER)
                    .build()
            )
            validate { credential ->
                if (credential.payload.audience.contains(Utils.JWT_AUDIENCE))
                    JWTPrincipal(credential.payload) else null
            }
        }
    }
}