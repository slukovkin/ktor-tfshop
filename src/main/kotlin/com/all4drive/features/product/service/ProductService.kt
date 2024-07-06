package com.all4drive.features.product.service

import com.all4drive.features.product.model.Product
import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.jetbrains.exposed.sql.transactions.transaction

class ProductService {

    object Products : Table() {
        val id = integer("id").autoIncrement()
        val code = integer("code")
        val article = varchar("article", length = 100)
        val title = varchar("title", length = 200)
        val qty = double("qty")
        val cross = integer("cross")

        override val primaryKey = PrimaryKey(id)
    }

    init {
        transaction {
            SchemaUtils.create(Products)
        }
    }

    private suspend fun <T> dbQuery(block: suspend () -> T): T =
        newSuspendedTransaction(Dispatchers.IO) { block() }

    suspend fun getAllProducts(): List<Product> {
        return dbQuery {
            Products.selectAll()
                .map {
                    Product(
                        it[Products.id],
                        it[Products.code],
                        it[Products.article],
                        it[Products.title],
                        it[Products.qty],
                        it[Products.cross]
                    )
                }
        }
    }

    suspend fun getProductByCode(code: Int): Product? {
        return dbQuery {
            Products.select { Products.code eq code }
                .map {
                    Product(
                        it[Products.id],
                        it[Products.code],
                        it[Products.article],
                        it[Products.title],
                        it[Products.qty],
                        it[Products.cross]
                    )
                }
                .singleOrNull()
        }
    }

    suspend fun getProductsByCross(cross: Int): List<Product> {
        return dbQuery {
            Products.select { Products.cross eq cross }
                .map {
                    Product(
                        it[Products.id],
                        it[Products.code],
                        it[Products.article],
                        it[Products.title],
                        it[Products.qty],
                        it[Products.cross]
                    )
                }
        }
    }

    suspend fun create(product: Product): Int = dbQuery {
        val isFound = getProductByCode(product.code)
        if (isFound != null) return@dbQuery 0
        Products.insert {
            it[code] = product.code
            it[article] = product.article
            it[title] = product.title
            it[qty] = product.qty
            it[cross] = product.cross
        }[Products.id]
    }

    suspend fun deleteProductByCode(code: Int) {
        dbQuery {
            Products.deleteWhere { Products.code eq code }
        }
    }

    suspend fun updateProductByCode(code: Int, product: Product) {
        dbQuery {
            Products.update({ Products.code eq code }) {
                it[article] = product.article
                it[title] = product.title
                it[qty] = product.qty
            }
        }
    }
}