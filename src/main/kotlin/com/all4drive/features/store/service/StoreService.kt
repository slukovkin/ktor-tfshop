package com.all4drive.features.store.service

import com.all4drive.features.store.model.Store
import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.jetbrains.exposed.sql.transactions.transaction

class StoreService(db: Database) {
    object Stores : Table() {
        val id = integer("id").autoIncrement()
        val title = varchar("store", length = 50)

        override val primaryKey = PrimaryKey(id)
    }

    init {
        transaction(db) {
            SchemaUtils.create(Stores)
        }
    }

    private suspend fun <T> dbQuery(block: suspend () -> T): T =
        newSuspendedTransaction(Dispatchers.IO) { block() }

    suspend fun createStore(store: Store): Int = dbQuery {
        val isFoundStore = getStoreByTitle(store.title)
        if (isFoundStore != null) return@dbQuery 0
        Stores.insert {
            it[title] = store.title
        }[Stores.id]
    }

    suspend fun getStoreByTitle(title: String): Store? {
        return dbQuery {
            Stores.select(Stores.title eq title)
                .map { Store(it[Stores.id], it[Stores.title]) }
                .singleOrNull()
        }
    }

    suspend fun deleteStore(id: Int): Int {
        return dbQuery {
            Stores.deleteWhere { Stores.id eq id }
        }
    }
}