package com.all4drive.features.shop.models.order

import kotlinx.serialization.Serializable

@Serializable
data class ResponseOnProductsInOrder(
    val id: Int,
    val code: Int,
    val article: String,
    val title: String,
    val qty: Double,
    val storeId: Int
)
