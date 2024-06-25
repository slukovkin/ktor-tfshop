package com.all4drive.features.product.routes

import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.productRoutes() {
    route("/api/products") {
        get {
            call.respond("Products list")
        }
    }
}

fun Application.configureProductRouting() {
    routing {
        productRoutes()
    }
}