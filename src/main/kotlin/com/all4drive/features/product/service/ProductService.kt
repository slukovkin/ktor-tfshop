package com.all4drive.features.product.service

import com.all4drive.features.product.model.Product
import com.all4drive.features.product.model.ProductInStoreRespond
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

    object ProductInStore : Table() {
        val id = integer("prId").autoIncrement()
        val idStore = integer("idStore")
        val idProduct = integer("idProduct")
        var productQty = double("productQty")
        var productPriceIn = double("productPriceIn")

        override val primaryKey = PrimaryKey(id)
    }

    init {
        transaction {
            SchemaUtils.create(Products)
            SchemaUtils.create(ProductInStore)
        }
    }

    private suspend fun <T> dbQuery(block: suspend () -> T): T =
        newSuspendedTransaction(Dispatchers.IO) { block() }

    //TODO не работает обновление количества!
    suspend fun insertProductToStore(storeId: Int, productId: Int, qty: Double, priceIn: Double) {
        dbQuery {
            ProductInStore.insert {
                it[idStore] = storeId
                it[idProduct] = productId
                it[productQty] = qty
                it[productPriceIn] = priceIn
            }[ProductInStore.id]
        }
    }

    suspend fun updateProductOnStore(productId: Int, qty: Double, priceIn: Double) {
        dbQuery {
            ProductInStore.update({ ProductInStore.idProduct eq productId }) {
                it[productQty] = qty
                it[productPriceIn] = priceIn
            }
        }
    }

    suspend fun searchProductOnStoreById(id: Int): Boolean {
        return dbQuery {
            ProductInStore.select { ProductInStore.idProduct eq id }
                .empty()
        }
    }

    //TODO Вывод всего товара в магазине
    /**
    SELECT pr_code, pr_article, pr_title, product_qty FROM ProductInStore  LEFT JOIN Products ON Products.idProduct = ProductInStore.idProduct
    WHERE ProductInStore.idStore = storeId;
     **/

    suspend fun getProductsFromStore(storeId: Int): List<ProductInStoreRespond> {
        return dbQuery {
            ProductInStore.select { ProductInStore.idStore eq storeId }
                .map {
                    val product = getProductById(it[ProductInStore.idProduct])
                        ?: throw IllegalArgumentException("Product not found")
                    ProductInStoreRespond(
                        productId = product.id!!,
                        code = product.code,
                        article = product.article,
                        title = product.title,
                        productQty = it[ProductInStore.productQty],
                        productPriceIn = it[ProductInStore.productPriceIn]
                    )
                }
        }
    }

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

    private suspend fun getProductById(id: Int): Product? {
        return dbQuery {
            Products.select { Products.id eq id }
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