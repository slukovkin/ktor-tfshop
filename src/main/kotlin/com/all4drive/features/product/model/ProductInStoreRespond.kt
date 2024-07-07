package com.all4drive.features.product.model

import kotlinx.serialization.Serializable

@Serializable
data class ProductInStoreRespond(
    val productId: Int,
    val code: Int,
    val article: String,
    val title: String,
    val productQty: Double,
    val productPriceIn: Double
)
