package com.all4drive.features.user_module.models

@kotlinx.serialization.Serializable
data class ResponseFromSearchUser(
    val id: String,
    val email: String,
    val firstName: String = "",
    val lastName: String = "",
    val address: String = "",
    val urlAvatar: String = ""
)
