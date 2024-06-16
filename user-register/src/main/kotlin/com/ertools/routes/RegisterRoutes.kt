package com.ertools.routes

import com.ertools.commons.Utils
import com.ertools.dto.*
import com.ertools.encryption.CodeGenerator
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.serialization.gson.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.registerRoutes() {
    var tokenData = TokenData("", 0)

    route("/register") {
        post {
            HttpClient(CIO) {
                install(ContentNegotiation) {
                    gson()
                }
            }.use { client ->

                /** Authentication **/
                if(tokenData.timestamp + Utils.TOKEN_EXPIRING_TIME_MILLIS < System.currentTimeMillis()) {
                    val loginRequest = Credentials(
                        Utils.SYSTEM_STORAGE_LOGIN,
                        Utils.SYSTEM_STORAGE_PASSWORD
                    )
                    val authResponse = client.post(Utils.SYSTEM_STORAGE_LOGIN_ENDPOINT) {
                        contentType(ContentType.Application.Json)
                        setBody(loginRequest)
                    }
                    if(authResponse.status != HttpStatusCode.OK) {
                        call.respond(
                            status = authResponse.status,
                            message = authResponse.body<String>()
                        )
                        return@use
                    }
                    tokenData = TokenData(
                        authResponse.body<LoginResponse>().token,
                        System.currentTimeMillis()
                    )
                }

                /** Registration attempt **/
                val registerRequest = call.receive<RegisterRequest>()
                val userEntity = UserEntity(
                    name = registerRequest.name,
                    login = registerRequest.login,
                    password = registerRequest.password,
                    code = CodeGenerator().generateKey(Utils.KEY_LENGTH)
                )

                val registerResponse = client.post(Utils.SYSTEM_STORAGE_USER_ENDPOINT) {
                    contentType(ContentType.Application.Json)
                    header(HttpHeaders.Authorization, "Bearer ${tokenData.token}")
                    setBody(userEntity)
                }
                call.respond(
                    status = registerResponse.status,
                    message = registerResponse.body<String>()
                )
            }
        }
    }


}