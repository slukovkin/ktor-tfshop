package com.all4drive.features.shop.models.order

import kotlinx.serialization.Serializable

@Serializable
data class Order(
    val userId: Int,
    val orderId: Int,
    val storeId: Int,
    val productId: Int,
    val productQty: Double,
)
