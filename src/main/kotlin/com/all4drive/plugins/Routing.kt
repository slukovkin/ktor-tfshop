@file:Suppress("DEPRECATION")

package com.all4drive.plugins

import com.all4drive.features.auth_module.routes.configureAuthRouting
import com.all4drive.features.user_module.routes.configureUserRouting
import io.ktor.server.application.*
import io.ktor.server.http.content.*
import io.ktor.server.routing.*

fun Application.configureRouting() {
    configureAuthRouting()
    configureUserRouting()
    routing {
        static("/") {
            resources("static")
        }
    }
}
