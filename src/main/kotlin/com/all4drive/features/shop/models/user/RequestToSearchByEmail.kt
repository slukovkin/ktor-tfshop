package com.all4drive.features.shop.models.user

@kotlinx.serialization.Serializable
data class RequestToSearchByEmail(
    val email: String
)
