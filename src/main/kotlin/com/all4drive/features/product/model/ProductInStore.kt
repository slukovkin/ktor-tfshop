package com.all4drive.features.product.model

import kotlinx.serialization.Serializable

@Serializable
data class ProductInStore(
    val id: Int? = null,
    val idStore: Int,
    val idProduct: Int,
    val productQty: Double,
    val productPriceIn: Double
)
