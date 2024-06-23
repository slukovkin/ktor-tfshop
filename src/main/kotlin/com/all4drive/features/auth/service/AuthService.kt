package com.all4drive.features.auth.service

import com.all4drive.features.models.User
import java.util.*

class AuthService {
    private val users = com.all4drive.mock.users

    fun createUser(candidate: User): Boolean {
        if (candidate.email.trim().isNotEmpty() && candidate.password.trim().isNotEmpty()) {
            val user = User(
                id = UUID.randomUUID().toString(),
                email = candidate.email,
                password = candidate.password
            )
            users.add(user)
            return true
        }
        return false
    }
}