package com.all4drive.features.users.service

import com.all4drive.features.models.User

class UserService {
    private val users = com.all4drive.mock.users

    fun getAllUsers(): List<User> {
        return users
    }
}