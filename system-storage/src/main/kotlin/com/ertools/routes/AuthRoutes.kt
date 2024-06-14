package com.ertools.routes

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.ertools.commons.Utils
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import java.util.*

fun Route.authRoutes() {
    post("/login") {
        val credentials = call.receive<Credentials>()
        if (credentials.username == Utils.JWT_USER && credentials.password == Utils.JWT_PASSWORD) {
            val token = JWT.create()
                .withAudience(Utils.JWT_AUDIENCE)
                .withIssuer(Utils.JWT_ISSUER)
                .withClaim("username", credentials.username)
                .withExpiresAt(Date(System.currentTimeMillis() + 60000))
                .sign(Algorithm.HMAC256(Utils.JWT_SECRET))
            call.respond(mapOf("token" to token))
        } else {
            call.respond(HttpStatusCode.Unauthorized, "Invalid credentials")
        }
    }
}

data class Credentials(val username: String, val password: String)