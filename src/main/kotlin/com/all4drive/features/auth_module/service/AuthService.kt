package com.all4drive.features.auth_module.service

import com.all4drive.features.models.User
import com.all4drive.features.user_module.service.UserService

class AuthService(private val userService: UserService) {

    suspend fun registration(candidate: User): Boolean {
        userService.create(candidate)
        return false
    }

    fun login(email: String, password: String): Boolean {
//        return if (validateEmail(email) && checkPassword(password)) {
//            val user = users.find { user ->
//                user.email == email && comparingHashAndPassword(user.password, password.trim())
//            }
//            return user?.email?.isNotEmpty() ?: false
//        } else {
//            false
//        }
        return false
    }
}