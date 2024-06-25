@file:Suppress("DEPRECATION")

package com.all4drive.plugins

import com.all4drive.features.auth.routes.configureAuthRouting
import com.all4drive.features.product.routes.configureProductRouting
import com.all4drive.features.store.routes.configureStoreRouting
import com.all4drive.features.user.routes.configureUserRouting
import io.ktor.server.application.*
import io.ktor.server.http.content.*
import io.ktor.server.routing.*

fun Application.configureRouting() {
    configureAuthRouting()
    configureUserRouting()
    configureProductRouting()
    configureStoreRouting()
    routing {
        static("/") {
            resources("static")
        }
    }
}
