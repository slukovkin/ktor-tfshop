package com.all4drive.features.shop.services

import com.all4drive.features.shop.models.product.Product
import com.all4drive.features.shop.models.product.ProductInStoreRespond
import com.all4drive.features.shop.repositories.ProductRepository
import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.jetbrains.exposed.sql.transactions.transaction

class ProductService : ProductRepository {

    object Products : Table() {
        val id = integer("id").autoIncrement()
        val code = integer("code")
        val article = varchar("article", length = 100)
        val title = varchar("title", length = 200)
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

    override suspend fun getProductById(id: Int): Product? {
        return dbQuery {
            Products.select { Products.id eq id }
                .map {
                    Product(
                        it[Products.id],
                        it[Products.code],
                        it[Products.article],
                        it[Products.title],
                        it[Products.cross]
                    )
                }
                .singleOrNull()
        }
    }

    override suspend fun insertProductToStore(storeId: Int, productId: Int, qty: Double, priceIn: Double): Int {
        return dbQuery {
            ProductInStore.insert {
                it[idStore] = storeId
                it[idProduct] = productId
                it[productQty] = qty
                it[productPriceIn] = priceIn
            }[ProductInStore.id]
        }
    }

    override suspend fun updateProductOnStore(productId: Int, qty: Double, priceIn: Double) {
        dbQuery {
            ProductInStore.update({ ProductInStore.idProduct eq productId }) {
                it[productQty] = qty
                it[productPriceIn] = priceIn
            }
        }
    }

    override suspend fun searchProductOnStoreById(id: Int, storeId: Int): Boolean {
        return dbQuery {
            ProductInStore.select { (ProductInStore.idProduct eq id) and (ProductInStore.idStore eq storeId) }
                .empty()
        }
    }

    /**
    SELECT pr_code, pr_article, pr_title, product_qty FROM ProductInStore  LEFT JOIN Products ON Products.idProduct = ProductInStore.idProduct
    WHERE ProductInStore.idStore = storeId;
     **/

    override suspend fun getProductsFromStore(storeId: Int): List<ProductInStoreRespond> {
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

    override suspend fun getAllProducts(): List<Product> {
        return dbQuery {
            Products.selectAll()
                .map {
                    Product(
                        it[Products.id],
                        it[Products.code],
                        it[Products.article],
                        it[Products.title],
                        it[Products.cross]
                    )
                }
        }
    }

    override suspend fun getProductByCode(code: Int): Product? {
        return dbQuery {
            Products.select { Products.code eq code }
                .map {
                    Product(
                        it[Products.id],
                        it[Products.code],
                        it[Products.article],
                        it[Products.title],
                        it[Products.cross]
                    )
                }
                .singleOrNull()
        }
    }

    override suspend fun getProductsByCross(cross: Int): List<Product> {
        return dbQuery {
            Products.select { Products.cross eq cross }
                .map {
                    Product(
                        it[Products.id],
                        it[Products.code],
                        it[Products.article],
                        it[Products.title],
                        it[Products.cross]
                    )
                }
        }
    }

    override suspend fun create(product: Product): Int = dbQuery {
        val isFound = getProductByCode(product.code)
        if (isFound != null) return@dbQuery 0
        Products.insert {
            it[code] = product.code
            it[article] = product.article
            it[title] = product.title
            it[cross] = product.cross
        }[Products.id]
    }

    override suspend fun deleteProductByCode(code: Int): Int {
        return dbQuery {
            Products.deleteWhere { Products.code eq code }
        }
    }

    override suspend fun updateProductByCode(code: Int, product: Product): Int {
        return dbQuery {
            Products.update({ Products.code eq code }) {
                it[article] = product.article
                it[title] = product.title
            }
        }
    }
}