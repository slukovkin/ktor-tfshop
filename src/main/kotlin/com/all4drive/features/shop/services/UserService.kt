package com.all4drive.features.shop.services

import com.all4drive.features.shop.models.user.Role
import com.all4drive.features.shop.models.user.User
import com.all4drive.features.shop.repositories.UserRepository
import com.all4drive.utils.generateHashFromPassword
import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.jetbrains.exposed.sql.transactions.transaction

class UserService : UserRepository {
    object Users : Table() {
        val id = integer("id").autoIncrement()
        val email = varchar("email", length = 50)
        val password = varchar("password", length = 100)
        val firstName = varchar("first_name", length = 100)
        val lastName = varchar("last_name", length = 100)
        val address = varchar("address", length = 100)
        val avatarUrl = varchar("avatar", length = 200)
        val role = enumeration<Role>("role")

        override val primaryKey = PrimaryKey(id)
    }

    init {
        transaction {
            SchemaUtils.create(Users)
        }
    }

    private suspend fun <T> dbQuery(block: suspend () -> T): T =
        newSuspendedTransaction(Dispatchers.IO) { block() }

    override suspend fun getAllUsers(): List<User> {
        return dbQuery {
            Users.selectAll()
                .map {
                    User(
                        it[Users.id],
                        it[Users.email],
                        it[Users.password],
                        it[Users.firstName],
                        it[Users.lastName],
                        it[Users.address],
                        it[Users.avatarUrl],
                        it[Users.role]
                    )
                }
        }
    }

    override suspend fun getUserByEmail(email: String): User? {
        return dbQuery {
            Users.select { Users.email eq email }
                .map {
                    User(
                        it[Users.id],
                        it[Users.email],
                        it[Users.password],
                        it[Users.firstName],
                        it[Users.lastName],
                        it[Users.address],
                        it[Users.avatarUrl],
                        it[Users.role]
                    )
                }
                .singleOrNull()
        }
    }

    override suspend fun create(user: User): Int = dbQuery {
        val isFound = getUserByEmail(user.email)
        if (isFound != null) return@dbQuery 0
        Users.insert {
            it[email] = user.email
            it[password] = generateHashFromPassword(user.password)
            it[firstName] = user.firstName
            it[lastName] = user.lastName
            it[address] = user.address
            it[avatarUrl] = user.urlAvatar
            it[role] = user.role
        }[Users.id]
    }

    override suspend fun deleteUserById(id: Int): Int {
        return dbQuery {
            Users.deleteWhere { Users.id eq id }
        }
    }

    override suspend fun update(id: Int, user: User): Int {
        return dbQuery {
            Users.update({ Users.id eq id }) {
                it[email] = user.email
                it[password] = generateHashFromPassword(user.password)
                it[firstName] = user.firstName
                it[lastName] = user.lastName
                it[address] = user.address
                it[avatarUrl] = user.urlAvatar
                it[role] = user.role
            }
        }
    }
}