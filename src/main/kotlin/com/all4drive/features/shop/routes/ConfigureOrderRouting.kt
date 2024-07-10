package com.all4drive.features.shop.routes

import com.all4drive.features.shop.services.OrderService
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.orderRouting() {
    val orderService = OrderService()

    route("/api/order") {

        // Получение всех заказов по выбранному магазину
        get("/store/{storeId}") {
            val storeId = call.parameters["storeId"]?.toInt() ?: throw IllegalArgumentException("Failed StoreId")
            val orders = orderService.getAllOrdersByStoreId(storeId)
            call.respond(HttpStatusCode.OK, orders)
        }

        // Получение заказа по ID заказа
        get("/{orderId}") {
            val orderId = call.parameters["orderId"]?.toInt() ?: throw IllegalArgumentException("Failed OrderId")
            val order = orderService.getOrderByOrderId(orderId)
            if (order.isEmpty())
                call.respond(HttpStatusCode.NotFound)
            else
                call.respond(HttpStatusCode.OK, order)
        }

        // Получение всех заказов пользователя по ID полльзователя
        get("/user/{userId}") {}

        // Создзание заказа пользователя
        post("/add") {}

        // Изменение заказа пользователя
        patch("/update/{orderId}") {}

        // Удаление заказа пользователя по ID заказа
        delete("/{orderId}") {}

    }
}

fun Application.configureOrderRouting() {
    routing {
        orderRouting()
    }
}

