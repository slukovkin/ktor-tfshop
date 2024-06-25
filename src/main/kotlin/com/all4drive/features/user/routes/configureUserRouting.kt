package com.all4drive.features.user.routes

import com.all4drive.database.Db
import com.all4drive.features.models.User
import com.all4drive.features.user.service.UserService
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.userRoutes() {
    val userService = UserService(db = Db.database)

    route("/api/users") {
        get {
            val users = userService.getAllUsers()
            call.respond(users)
        }

        get("{email}") {
            val email = call.parameters["email"] ?: throw IllegalArgumentException("ID")
            val user = userService.getUserByEmail(email)
            call.respond(user ?: "User not found")
        }

        post {
            val candidate = call.receive<User>()
            val result = userService.create(candidate)
            if (result == 0)
                call.respond(HttpStatusCode.Conflict, "Пользователь уже существует в БД")
            else
                call.respond(HttpStatusCode.OK, "Пользователь успешно зарегистрирован в БД")
        }

        patch("{id}") {
            val id = call.parameters["id"]?.toInt() ?: throw IllegalArgumentException("ID")
            val candidate = call.receive<User>()
            userService.update(id, candidate)
            call.respond(HttpStatusCode.OK)
        }

        delete("{id}") {
            val id = call.parameters["id"]?.toInt() ?: throw IllegalArgumentException("ID")
            userService.deleteUserById(id)
            call.respond(HttpStatusCode.OK)
        }
    }
}

fun Application.configureUserRouting() {
    routing {
        userRoutes()
    }
}