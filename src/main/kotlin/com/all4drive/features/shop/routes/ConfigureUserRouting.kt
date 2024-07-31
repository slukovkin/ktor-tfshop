package com.all4drive.features.shop.routes

import com.all4drive.features.shop.models.user.User
import com.all4drive.features.shop.services.TokenService
import com.all4drive.features.shop.services.UserService
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import java.util.*

fun Route.userRoutes() {
    val userService = UserService()
    val tokenService = TokenService()

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
            val userId = userService.create(candidate)
            if (userId == 0) {
                call.respond(HttpStatusCode.Conflict, "Пользователь уже существует в БД")
            } else {
                val token = UUID.randomUUID().toString()
                tokenService.createToken(userId, token)

                call.respond(HttpStatusCode.OK, userId)
//                call.respond(HttpStatusCode.OK, "Пользователь успешно зарегистрирован в БД")
            }
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