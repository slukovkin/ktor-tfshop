@file:Suppress("DEPRECATION")

package com.all4drive.plugins

import com.all4drive.features.shop.routes.*
import io.ktor.server.application.*
import io.ktor.server.http.content.*
import io.ktor.server.routing.*

fun Application.configureRouting() {
    configureAuthRouting()
    configureUserRouting()
    configureProductRouting()
    configureStoreRouting()
    configureOrderRouting()
    routing {
        static("/") {
            resources("static")
        }
    }
}
