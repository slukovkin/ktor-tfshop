package com.all4drive.features.shop.models.product

import kotlinx.serialization.Serializable

@Serializable
data class ProductInStoreReceive(
    val storeId: Int,
    val productId: Int,
    val productQty: Double,
    val productPriceIn: Double
)
