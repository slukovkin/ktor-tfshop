package com.all4drive.features.shop.models.product

import kotlinx.serialization.Serializable

@Serializable
data class Product(
    val id: Int? = null,
    val code: Int,
    val article: String,
    val title: String,
    val cross: Int
)
