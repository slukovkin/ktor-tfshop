package com.all4drive.plugins

import com.all4drive.features.shop.routes.*
import io.ktor.server.application.*

fun Application.configureRouting() {
    configureAuthRouting()
    configureUserRouting()
    configureProductRouting()
    configureStoreRouting()
    configureOrderRouting()
}
