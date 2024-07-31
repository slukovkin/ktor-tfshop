package com.all4drive.features.shop.services

import com.all4drive.features.shop.models.token.Token
import com.all4drive.features.shop.repositories.TokenRepository
import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.jetbrains.exposed.sql.transactions.transaction

class TokenService : TokenRepository {

    object Tokens : Table() {
        val id = integer("id").autoIncrement()
        val userId = integer("user_id").uniqueIndex()
        val token = varchar("token", length = 250)

        override val primaryKey = PrimaryKey(id)
    }

    init {
        transaction {
            SchemaUtils.create(Tokens)
        }
    }

    private suspend fun <T> dbQuery(block: suspend () -> T): T =
        newSuspendedTransaction(Dispatchers.IO) { block() }


    override suspend fun createToken(userId: Int, token: String) {
        transaction {
            Tokens.insert {
                it[Tokens.userId] = userId
                it[Tokens.token] = token
            }
        }
    }

    override suspend fun getTokenByUserId(userId: Int): Token? {
        return dbQuery {
            Tokens.select { Tokens.userId eq userId }
                .map {
                    Token(
                        it[Tokens.id],
                        it[Tokens.token]
                    )
                }.singleOrNull()
        }
    }
}