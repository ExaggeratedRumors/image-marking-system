package com.ertools.plugins

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.ertools.commons.Utils
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.response.*


fun Application.configureSecurity() {
    install(Authentication) {
        jwt(Utils.JWT_AUTHENTICATION_NAME) {
            realm = Utils.JWT_AUTHENTICATION_REALM
            verifier(
                JWT.require(Algorithm.HMAC256(Utils.JWT_AUTHENTICATION_SECRET))
                    .withAudience(Utils.JWT_AUTHENTICATION_AUDIENCE)
                    .withIssuer(Utils.JWT_AUTHENTICATION_ISSUER)
                    .build()
            )
            validate {credential ->
                if(credential.payload.audience.contains(Utils.JWT_AUTHENTICATION_AUDIENCE))
                    JWTPrincipal(credential.payload)
                else null
            }
            challenge { _, _ ->
                call.respond(HttpStatusCode.Unauthorized, "Token is not valid or has expired")
            }
        }
    }
}