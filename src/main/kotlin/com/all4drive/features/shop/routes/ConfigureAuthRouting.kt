package com.all4drive.features.shop.routes

import com.all4drive.features.shop.models.token.TokenResponse
import com.all4drive.features.shop.models.user.User
import com.all4drive.features.shop.services.AuthService
import com.all4drive.features.shop.services.TokenService
import com.all4drive.features.shop.services.UserService
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import java.util.*

fun Route.authRoutes() {
    val authService = AuthService(userService = UserService())
    val tokenService = TokenService()

    route("/api/auth") {
        post("/login") {
            try {
                val candidate = call.receive<User>()
                val resultRequest = authService.login(candidate.email, candidate.password)
                if (resultRequest) {
//                    call.respond(HttpStatusCode.OK, "Доступ разрешен")
                    call.respond(HttpStatusCode.OK, resultRequest)
                } else {
                    call.respond(HttpStatusCode.OK, resultRequest)
//                    call.respond(HttpStatusCode.Forbidden, "Ошибка логина или пароля")
                }
            } catch (err: Error) {
                call.respond(HttpStatusCode.BadRequest, "Ошибка запроса")
            }

        }

        post("/registration") {
            try {
                val user = call.receive<User>()
                val userId = authService.registration(user)
                if (userId > 0) {
                    val token = UUID.randomUUID().toString()
                    tokenService.createToken(userId, token)
                    val response = TokenResponse(userId, token)
                    call.respond(HttpStatusCode.OK, response)
//                    call.respond(HttpStatusCode.OK, "Пользователь успешно зарегистрирован")
                } else {
                    call.respond(HttpStatusCode.OK)
//                    call.respond(HttpStatusCode.OK, "Ошибка регистрации. Проверьте регистрационные данные")
                }
            } catch (err: Error) {
                call.respond(HttpStatusCode.BadRequest, "Произошла ошибка: $err")
            }
        }
    }
}

fun Application.configureAuthRouting() {
    routing {
        authRoutes()
    }
}