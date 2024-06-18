package com.ertools.routes

import com.ertools.authentication.TokenData
import com.ertools.authentication.TokenManager
import com.ertools.commons.Utils
import com.ertools.dto.request.FetchRequest
import com.ertools.dto.response.ImageResponse
import com.ertools.dto.response.UserResponse
import com.ertools.marker.ImageManager
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.serialization.gson.*
import io.ktor.server.application.*
import io.ktor.server.plugins.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import javax.naming.AuthenticationException

fun Route.fetchRoutes() {
    val tokenData = TokenData("", 0)

    route("/fetch") {
        post {
            var imageData: ImageResponse? = null
            var userData: UserResponse? = null

            /** Validate object **/
            val imageRequest: FetchRequest
            try {
                imageRequest = call.receive<FetchRequest>()
            } catch (e: Exception) {
                e.printStackTrace()
                call.respond(
                    status = HttpStatusCode.BadRequest,
                    message = "Request body is incorrect."
                )
                return@post
            }

            /** Connect with storage service **/
            try {
                HttpClient(CIO) {
                    install(ContentNegotiation) {
                        gson()
                    }
                }.use { client ->
                    /** Authenticate **/
                    TokenManager().updateToken(tokenData, client)

                    /** User verification **/
                    val userResponse = client.get("${Utils.SYSTEM_STORAGE_USER_ENDPOINT}/${imageRequest.login}") {
                        contentType(ContentType.Application.Json)
                        header(HttpHeaders.Authorization, "Bearer ${tokenData.token}")
                    }
                    if (userResponse.status != HttpStatusCode.OK) {
                        call.respond(
                            status = userResponse.status,
                            message = userResponse.body<String>()
                        )
                        throw NotFoundException()
                    }
                    userData = userResponse.body<UserResponse>()
                    if (userData?.password != imageRequest.password) {
                        call.respond(
                            status = HttpStatusCode.Unauthorized,
                            message = "Authentication data is incorrect."
                        )
                        throw AuthenticationException()
                    }

                    /** Fetch image **/
                    val imageResponse = client.get("${Utils.SYSTEM_STORAGE_IMAGE_ENDPOINT}/${imageRequest.imageName}") {
                        contentType(ContentType.Application.Json)
                        header(HttpHeaders.Authorization, "Bearer ${tokenData.token}")
                    }
                    if (imageResponse.status != HttpStatusCode.OK) {
                        call.respond(
                            status = userResponse.status,
                            message = userResponse.body<String>()
                        )
                        throw NotFoundException()
                    }
                    imageData = imageResponse.body<ImageResponse>()
                }
            } catch (e: AuthenticationException) {
                call.respond(
                    status = HttpStatusCode.Unauthorized,
                    message = "Error during authentication."
                )
            } catch (e: Exception) {
                e.printStackTrace()
                return@post
            }

            /** Image processing  **/
            val file = ImageManager().saveDataAsImage(imageData?.data!!, imageData?.name!!)
            ImageManager().markImage(file, userData?.code!!)
            if(file.exists()) call.respondFile(file)
            else call.respond(HttpStatusCode.NotFound)
        }
    }
}