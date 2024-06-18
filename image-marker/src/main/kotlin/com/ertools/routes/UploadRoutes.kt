package com.ertools.routes

import com.ertools.authentication.TokenData
import com.ertools.authentication.TokenManager
import com.ertools.commons.Utils
import com.ertools.dto.request.UploadRequest
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.http.content.*
import io.ktor.serialization.gson.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import java.util.*
import javax.naming.AuthenticationException

fun Route.uploadRoutes() {
    val tokenData = TokenData("", 0)

    route("/upload") {
        post {
            var name: String? = null
            var imageData: ByteArray? = null

            /** Receive file **/
            val multipart = call.receiveMultipart()
            multipart.forEachPart { part ->
                when (part) {
                    is PartData.FileItem -> {
                        if (part.name == Utils.CLIENT_FILE_TAG) {
                            name = part.originalFileName
                            imageData = part.streamProvider().use { it.readBytes() }
                        }
                    }
                    else -> {}
                }
                part.dispose()
            }
            if(name == null || imageData == null) {
                call.respond(
                    status = HttpStatusCode.BadRequest,
                    message = "Cannot receive image."
                )
                return@post
            }

            /** Connect with service **/
            try {
                HttpClient(CIO) {
                    install(ContentNegotiation) {
                        gson()
                    }
                }.use { client ->
                    /** Authentication **/
                    TokenManager().updateToken(tokenData, client)

                    /** Upload file **/
                    val encodedImage = Base64.getEncoder().encodeToString(imageData)
                    val uploadRequest = UploadRequest(name!!, encodedImage)
                    val uploadResponse = client.post(Utils.SYSTEM_STORAGE_IMAGE_ENDPOINT) {
                        contentType(ContentType.Application.Json)
                        header(HttpHeaders.Authorization, "Bearer ${tokenData.token}")
                        setBody(uploadRequest)
                    }

                    /** Response success **/
                    call.respond(
                        status = uploadResponse.status,
                        message = uploadResponse.body<String>()
                    )
                }
            } catch (e: AuthenticationException) {
                call.respond(
                    status = HttpStatusCode.Unauthorized,
                    message = "Error during authentication."
                )
                e.printStackTrace()
                return@post
            }
        }
    }
}
