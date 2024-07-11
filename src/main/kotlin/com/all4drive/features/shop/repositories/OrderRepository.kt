package com.all4drive.features.shop.repositories

import com.all4drive.features.shop.models.order.Order
import com.all4drive.features.shop.models.order.ResponseOnProductsInOrder
import com.all4drive.features.shop.models.order.ResponseToUserOrders


interface OrderRepository {

    // Получение всех заказов по выбранному магазину
    suspend fun getAllOrdersByStoreId(storeId: Int): List<Order>

    // Получение заказа по ID заказа
    suspend fun getOrderByOrderId(orderId: Int): List<ResponseOnProductsInOrder>

    // Получение всех заказов пользователя по ID полльзователя
    suspend fun getAllOrdersByUserId(userId: Int): List<ResponseToUserOrders>

    // Создзание заказа пользователя
    suspend fun createOrder(order: Order): Int

    // Изменение заказа пользователя
    suspend fun updateOrderByOrderId(orderId: Int): Boolean

    // Удаление заказа пользователя по ID заказа
    suspend fun deleteOrderByOrderId(orderId: Int): Int

}