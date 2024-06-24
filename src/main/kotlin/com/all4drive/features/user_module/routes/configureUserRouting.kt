package com.all4drive.features.user_module.routes

import com.all4drive.features.user_module.models.RequestToSearchByEmail
import com.all4drive.features.user_module.service.UserService
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.userRoutes() {
    val userService = UserService()

    route("/api/users") {
        get {
            val users = userService.getAllUsers()
            call.respond(users)
        }

        post() {
            val candidate = call.receive<RequestToSearchByEmail>()
            val user = userService.getUserByEmail(candidate.email)
            call.respond(user ?: "User not found")
        }
    }
}

fun Application.configureUserRouting() {
    routing {
        userRoutes()
    }
}