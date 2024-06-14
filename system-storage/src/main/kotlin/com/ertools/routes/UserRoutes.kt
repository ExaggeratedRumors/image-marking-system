package com.ertools.routes

import com.ertools.domain.UserRepository
import com.ertools.dto.request.UserRequest
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject

fun Route.userRoutes() {
    val repository by inject<UserRepository>()

    route("/user") {
        post {
            val user = call.receive<UserRequest>()
            val insertedId = repository.insertOne(user.toDataObject())
                ?: return@post call.respondText(
                    "User already registered",
                    status = HttpStatusCode.Forbidden
                )
            call.respond(HttpStatusCode.Created, "Created user with id $insertedId")
        }

        delete("/{login?}") {
            val login = call.parameters["login"] ?: return@delete call.respondText(
                text = "Missing user login",
                status = HttpStatusCode.BadRequest
            )
            val delete: Long = repository.deleteByLogin(login)
            if (delete == 1L) {
                return@delete call.respondText("User deleted successfully", status = HttpStatusCode.OK)
            }
            return@delete call.respondText("User not found", status = HttpStatusCode.NotFound)
        }

        get("/{login?}") {
            val login = call.parameters["login"]
            if (login.isNullOrEmpty()) {
                return@get call.respondText(
                    text = "Missing login",
                    status = HttpStatusCode.BadRequest
                )
            }
            repository.findByLogin(login)?.let {
                call.respond(it.toResponse())
            } ?: call.respondText("No records found for login $login")
        }

        patch("/{login?}") {
            val login = call.parameters["login"] ?: return@patch call.respondText(
                text = "Missing user login",
                status = HttpStatusCode.BadRequest
            )
            val updated = repository.updateOne(login, call.receive())
            call.respondText(
                text = if (updated == 1L) "User updated successfully" else "User not found",
                status = if (updated == 1L) HttpStatusCode.OK else HttpStatusCode.NotFound
            )
        }

        /*authenticate(Utils.AUTHENTICATION_USER) {
            get("/me") {
                val principal = call.principal<UserIdPrincipal>()
                val user = principal?.let { repository.findByLogin(it.name) }
                if (user != null) {
                    call.respond(user)
                } else {
                    call.respond(HttpStatusCode.Unauthorized, "User not found")
                }
            }
        }*/
    }
}