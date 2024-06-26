package com.all4drive.features.product.routes

import com.all4drive.features.product.model.Product
import com.all4drive.features.product.service.ProductService
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.productRoutes() {
    val productService = ProductService()

    route("/api/products") {
        get {
            val products = productService.getAllProducts()
            call.respond(HttpStatusCode.OK, products)
        }

        get("/code/{code}") {
            val code = call.parameters["code"]?.toInt() ?: throw IllegalArgumentException("CODE")
            val product = productService.getProductByCode(code) ?: return@get
            call.respond(product)
        }

        get("/cross/{cross}") {
            val cross = call.parameters["cross"]?.toInt() ?: throw IllegalArgumentException("CROSS CODE")
            val products = productService.getProductsByCross(cross)
            call.respond(products)
        }

        post {
            val candidate = call.receive<Product>()
            if (productService.create(candidate) != 0)
                call.respond(HttpStatusCode.OK)
            else
                call.respond(HttpStatusCode.Conflict)
        }

        patch("{code}") {
            val code = call.parameters["code"]?.toInt() ?: throw IllegalArgumentException("CODE")
            val product = call.receive<Product>()
            productService.updateProductByCode(code, product)
            call.respond(HttpStatusCode.OK)
        }

        delete("{code}") {
            val code = call.parameters["code"]?.toInt() ?: throw IllegalArgumentException("CODE")
            productService.deleteProductByCode(code)
            call.respond(HttpStatusCode.OK)
        }
    }
}

fun Application.configureProductRouting() {
    routing {
        productRoutes()
    }
}