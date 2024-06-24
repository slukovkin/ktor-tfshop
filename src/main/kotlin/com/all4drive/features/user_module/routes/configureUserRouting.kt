package com.all4drive.features.user_module.routes

import com.all4drive.features.user_module.models.RequestToSearchByEmail
import com.all4drive.features.user_module.models.ResponseFromSearchUser
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
            call.respond(mapOf("users" to users))
        }

        post() {
            val candidate = call.receive<RequestToSearchByEmail>()
            val user = userService.getUserByEmail(candidate.email)

            if (user != null) {
                val resultRequestByUser = ResponseFromSearchUser(
                    id = user.id,
                    email = user.email,
                    firstName = user.firstName,
                    lastName = user.lastName,
                    address = user.address,
                    urlAvatar = user.urlAvatar
                )
                call.respond(resultRequestByUser)
            }
            call.respond("User not found")
        }
    }
}

fun Application.configureUserRouting() {
    routing {
        userRoutes()
    }
}