package com.all4drive.features.shop.models.token

@kotlinx.serialization.Serializable
data class TokenResponse(
    val userId: Int,
    val token: String
)
