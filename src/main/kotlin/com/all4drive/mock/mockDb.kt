package com.all4drive.mock

import com.all4drive.features.models.User
import java.util.*

val users = mutableListOf(
    User(UUID.randomUUID().toString(), "admin@gmail.com", "123456"),
    User(UUID.randomUUID().toString(), "user@gmail.com", "123456"),
)