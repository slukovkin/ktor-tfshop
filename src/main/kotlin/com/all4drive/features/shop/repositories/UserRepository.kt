package com.all4drive.features.shop.repositories

import com.all4drive.features.shop.models.user.User

interface UserRepository {

    suspend fun getAllUsers(): List<User>

    suspend fun getUserByEmail(email: String): User?

    suspend fun create(user: User): Int

    suspend fun deleteUserById(id: Int): Int

    suspend fun update(id: Int, user: User): Int
}