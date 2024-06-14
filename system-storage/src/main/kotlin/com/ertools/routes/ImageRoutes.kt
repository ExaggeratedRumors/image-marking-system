package com.ertools.routes

import com.ertools.domain.ImageRepository
import com.ertools.dto.request.ImageRequest
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject

fun Route.imageRoutes() {
    val repository by inject<ImageRepository>()

    route("image") {
        post {
            val image = call.receive<ImageRequest>()
            val insertedId = repository.insert(image.toDataObject())
                ?: return@post call.respondText("Image already registered", status = HttpStatusCode.Forbidden)
            call.respond(HttpStatusCode.Created, "Created image with id $insertedId")
        }

        delete("/{name?}") {
            val name = call.parameters["name"] ?: return@delete call.respondText(
                text = "Missing image name",
                status = HttpStatusCode.BadRequest
            )
            val delete: Long = repository.deleteByName(name)
            if(delete == 1L) return@delete call.respondText(
                text = "Image deleted successfully",
                status = HttpStatusCode.OK
            )
            return@delete call.respondText(
                text = "Image not found",
                status = HttpStatusCode.NotFound
            )
        }

        get {
            call.respond(repository.selectAll())
        }

        get("/{name?}") {
            val name = call.parameters["name"]
            if(name.isNullOrEmpty()) return@get call.respondText(
                text = "Missing name",
                status = HttpStatusCode.BadRequest
            )
            repository.findByName(name)?.let {
                call.respond(it.toResponse())
            } ?: call.respondText(
                text = "No records found for name $name",
                status = HttpStatusCode.NotFound
            )
        }

        patch("/{name?}") {
            val name = call.parameters["name"] ?: return@patch call.respondText(
                text = "Missing image name",
                status = HttpStatusCode.BadRequest
            )
            val updated = repository.updateByName(name, call.receive())
            call.respondText(
                text = if (updated == 1L) "Image updated successfully" else "Image not found",
                status = if (updated == 1L) HttpStatusCode.OK else HttpStatusCode.NotFound
            )
        }
    }
}