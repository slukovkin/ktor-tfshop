package com.all4drive.features.auth.routes

import com.all4drive.features.auth.service.AuthService
import com.all4drive.features.models.User
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.authRoutes() {
    val authService = AuthService()

    route("/api/auth") {
        post("/login") {
            call.respondText("Login", ContentType.Any, HttpStatusCode.OK)
        }

        post("/registration") {
            try {
                val user = call.receive<User>()
                if (authService.createUser(user)) {
                    call.respond(HttpStatusCode.OK, "Пользователь успешно зарегистрирован")
                } else {
                    call.respond(HttpStatusCode.OK, "Ошибка регистрации. Проверьте регистрационные данные")
                }
            } catch (err: Error) {
                call.respondText { "Произошла ошибка: $err" }
            }
        }
    }
}

fun Application.configureAuthRouting() {
    routing {
        authRoutes()
    }
}