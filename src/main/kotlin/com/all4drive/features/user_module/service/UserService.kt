package com.all4drive.features.user_module.service

import com.all4drive.features.models.User

class UserService {
    private val users = com.all4drive.mock.users

    fun getAllUsers(): List<User> {
        return users
    }

    fun getUserByEmail(email: String): User? {
        val user = users.find { user: User ->
            user.email == email
        }

        return user
    }
}