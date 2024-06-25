package com.all4drive.features.auth_module.service

import com.all4drive.features.models.User
import com.all4drive.features.user_module.service.UserService
import com.all4drive.features.utils.comparingHashAndPassword

class AuthService(private val userService: UserService) {

    suspend fun registration(candidate: User): Boolean {
        userService.create(candidate)
        return false
    }

    suspend fun login(email: String, password: String): Boolean {
        val user = userService.getUserByEmail(email) ?: return false

        return comparingHashAndPassword(user.password, password)
    }
}