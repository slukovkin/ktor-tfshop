package com.all4drive.features.models

@kotlinx.serialization.Serializable
data class User(
    val id: String = "",
    val email: String,
    val password: String,
    val firstName: String = "",
    val lastName: String = "",
    val address: String = "",
    val urlAvatar: String = ""
)
