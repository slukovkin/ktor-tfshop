package com.all4drive.features.shop.models.token

@kotlinx.serialization.Serializable
data class Token(
    val id: Int? = null,
    val token: String
)
