package com.ertools.plugins

import io.ktor.server.application.*
import io.ktor.auth.*
import io.ktor.auth.jwt.*
import org.koin.ktor.ext.inject

/**
 * JWT token configuration
 */
fun Application.configureSecurity() {

    val jwtConfig: JWTConfig by inject()

    install(Authentication) {
        jwt {
            verifier(jwtConfig.verifier)
            validate {
                val claim = it.payload.getClaim(JWTConfigImpl.CLAIM).asString()
                if (claim != null) {
                    UserIdPrincipalForUser(claim)
                } else null
            }
        }
    }
}