package com.ertools.routes

import com.ertools.commons.Utils
import com.ertools.dto.RegisterRequest
import com.ertools.dto.UserEntity
import com.ertools.encryption.CodeGenerator
import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.registerRoutes() {
    route("/register") {
        post {
            val registerRequest = call.receive<RegisterRequest>()
            call.respond(
                status = HttpStatusCode.Created,
                message = "User registered in system: $registerRequest"
            )
            val userEntity = UserEntity(
                name = registerRequest.name,
                login = registerRequest.login,
                password = registerRequest.password,
                code = CodeGenerator().generateKey(Utils.KEY_LENGTH)
            )
            HttpClient().use { client ->
                val response = client.post(Utils.SYSTEM_STORAGE_ENDPOINT) {
                    contentType(ContentType.Application.Json)
                    setBody(userEntity)
                }
                println("Server response: ${response.toString()}")
                call.respond(
                    status = response.status,
                    message = "Got it"
                )
            }
        }
    }
}