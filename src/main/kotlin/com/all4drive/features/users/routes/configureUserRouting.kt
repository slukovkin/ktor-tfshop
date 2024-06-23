package com.all4drive.features.users.routes

import com.all4drive.features.users.service.UserService
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.userRoutes() {
    val userService: UserService = UserService()

    route("/api/users") {
        get {
            val users = userService.getAllUsers()
            call.respond(mapOf("users" to users))
        }
    }
}

fun Application.configureUserRouting() {
    routing {
        userRoutes()
    }
}