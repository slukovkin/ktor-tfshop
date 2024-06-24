package com.all4drive.features.user_module.service

import com.all4drive.features.models.User
import com.all4drive.features.user_module.models.ResponseFromSearchUser

class UserService {
    private val users = com.all4drive.mock.users

    fun getAllUsers(): List<User> {
        return users
    }

    fun getUserByEmail(email: String): ResponseFromSearchUser? {
        val user = users.find { user: User ->
            user.email == email
        }
        if (user != null) {
            return ResponseFromSearchUser(
                id = user.id,
                email = user.email,
                firstName = user.firstName,
                lastName = user.lastName,
                address = user.address,
                urlAvatar = user.urlAvatar
            )
        }

        return user
    }

}