package com.all4drive.features.shop.models.order

import kotlinx.serialization.Serializable

@Serializable
data class OrderResponse(
    val code: Int,
    val article: String,
    val title: String,
    val qty: Double
)
