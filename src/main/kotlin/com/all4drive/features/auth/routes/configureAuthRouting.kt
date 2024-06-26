package com.all4drive.features.auth.routes

import com.all4drive.database.Db
import com.all4drive.features.auth.service.AuthService
import com.all4drive.features.user.models.User
import com.all4drive.features.user.service.UserService
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.authRoutes() {
    val authService = AuthService(userService = UserService(db = Db.database))

    route("/api/auth") {
        post("/login") {
            try {
                val candidate = call.receive<User>()
                val resultRequest = authService.login(candidate.email, candidate.password)
                if (resultRequest) {
                    call.respondText("Доступ разрешен", ContentType.Any, HttpStatusCode.OK)
                } else {
                    call.respondText("Ошибка логина или пароля", ContentType.Any, HttpStatusCode.Forbidden)
                }
            } catch (err: Error) {
                call.respondText("Ошибка запроса", ContentType.Any, HttpStatusCode.BadRequest)
            }

        }

        post("/registration") {
            try {
                val user = call.receive<User>()
                if (authService.registration(user)) {
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