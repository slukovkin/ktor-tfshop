package com.all4drive.features.shop.repositories

import com.all4drive.features.shop.models.token.Token

interface TokenRepository {
    suspend fun createToken(userId: Int, token: String)

    suspend fun getTokenByUserId(userId: Int): Token?
}