package com.ertools.routes

import com.ertools.commons.Utils
import com.ertools.domain.UserRepository
import com.ertools.domain.UserRequest
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.bson.types.ObjectId
import org.koin.ktor.ext.inject

fun Route.userRoutes() {
    val repository: UserRepository by inject<UserRepository>()

    route("/user") {
        post {
            val user = call.receive<UserRequest>()
            val insertedId = repository.insertOne(user.toDataObject())
            call.respond(HttpStatusCode.Created, "Created user with id $insertedId")
        }

        delete("/{id?}") {
            val id = call.parameters["id"] ?: return@delete call.respondText(
                text = "Missing user id",
                status = HttpStatusCode.BadRequest
            )
            val delete: Long = repository.deleteById(ObjectId(id))
            if (delete == 1L) {
                return@delete call.respondText("Fitness Deleted successfully", status = HttpStatusCode.OK)
            }
            return@delete call.respondText("Fitness not found", status = HttpStatusCode.NotFound)
        }

        get("/{id?}") {
            val id = call.parameters["id"]
            if (id.isNullOrEmpty()) {
                return@get call.respondText(
                    text = "Missing id",
                    status = HttpStatusCode.BadRequest
                )
            }
            repository.findById(ObjectId(id))?.let {
                call.respond(it.toResponse())
            } ?: call.respondText("No records found for id $id")
        }

        patch("/{id?}") {
            val id = call.parameters["id"] ?: return@patch call.respondText(
                text = "Missing user id",
                status = HttpStatusCode.BadRequest
            )
            val updated = repository.updateOne(ObjectId(id), call.receive())
            call.respondText(
                text = if (updated == 1L) "User updated successfully" else "User not found",
                status = if (updated == 1L) HttpStatusCode.OK else HttpStatusCode.NotFound
            )
        }

        authenticate(Utils.AUTHENTICATION_USER) {
            get("/me") {
                val principal = call.principal<UserIdPrincipal>()
                val user = principal?.let { repository.findByLogin(it.name) }
                if (user != null) {
                    call.respond(user)
                } else {
                    call.respond(HttpStatusCode.Unauthorized, "User not found")
                }
            }
        }
    }
}