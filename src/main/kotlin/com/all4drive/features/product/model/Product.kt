package com.all4drive.features.product.model

@kotlinx.serialization.Serializable
data class Product(
    val id: Int? = null,
    val code: Int,
    val article: String,
    val title: String,
    val qty: Double = 0.0,
    val cross: Int
)
