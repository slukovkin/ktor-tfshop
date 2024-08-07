package com.all4drive.features.shop.services

import com.all4drive.features.shop.models.user.User
import com.all4drive.features.shop.repositories.AuthRepository
import com.all4drive.utils.comparingHashAndPassword

class AuthService(private val userService: UserService) : AuthRepository {

    override suspend fun registration(candidate: User): Boolean {
        userService.create(candidate)
        return false
    }

    override suspend fun login(email: String, password: String): Boolean {
        val user = userService.getUserByEmail(email) ?: return false

        return comparingHashAndPassword(user.password, password)
    }
}