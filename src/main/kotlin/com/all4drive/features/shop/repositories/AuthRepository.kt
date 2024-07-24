package com.all4drive.features.shop.repositories

import com.all4drive.features.shop.models.user.User

interface AuthRepository {

    suspend fun registration(candidate: User): Int

    suspend fun login(email: String, password: String): Boolean
}