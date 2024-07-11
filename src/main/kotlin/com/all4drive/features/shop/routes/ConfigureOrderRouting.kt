package com.all4drive.features.shop.routes

import com.all4drive.features.shop.models.order.Order
import com.all4drive.features.shop.services.OrderService
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
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
        get("/user/{userId}") {
            val userId = call.parameters["userId"]?.toInt() ?: throw IllegalArgumentException("Failed UserId")
            val orders = orderService.getAllOrdersByUserId(userId)
            if (orders.isEmpty())
                call.respond(HttpStatusCode.NotFound)
            else
                call.respond(HttpStatusCode.OK, orders)
        }

        // Создзание заказа пользователя
        post("/add") {
            val order = call.receive<Order>()
            val answer = orderService.createOrder(order)
            if (answer == 0)
                call.respond(HttpStatusCode.BadRequest, "Product exists in order")
            else
                call.respond(HttpStatusCode.OK, "Order successfully")
        }

        // Изменение заказа пользователя
        patch("/update/{orderId}") {}

        // Удаление заказа пользователя по ID заказа
        delete("/delete/{orderId}") {
            val orderId = call.parameters["orderId"]?.toInt() ?: throw IllegalArgumentException("Failed OrderId")
            val answer = orderService.deleteOrderByOrderId(orderId)
            if (answer == 0)
                call.respond(HttpStatusCode.BadRequest)
            else
                call.respond(HttpStatusCode.OK, "Order is removed")
        }

    }
}

fun Application.configureOrderRouting() {
    routing {
        orderRouting()
    }
}

