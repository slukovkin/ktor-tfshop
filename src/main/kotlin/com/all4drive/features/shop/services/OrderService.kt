package com.all4drive.features.shop.services

import com.all4drive.features.shop.models.order.Order
import com.all4drive.features.shop.models.order.ProductInOrderResponse
import com.all4drive.features.shop.repositories.OrderRepository
import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.jetbrains.exposed.sql.transactions.transaction

class OrderService : OrderRepository {

    object Orders : Table() {
        val id = integer("id").autoIncrement()
        val userId = integer("userId").references(UserService.Users.id)
        val orderId = integer("orderId")
        val storeId = integer("storeId").references(StoreService.Stores.id)
        val productId = integer("productId").references(ProductService.Products.id)
        val productQty = double("productQty")

        override val primaryKey = PrimaryKey(id)
    }

    init {
        transaction {
            SchemaUtils.create(Orders)
        }
    }

    private suspend fun <T> dbQuery(block: suspend () -> T): T =
        newSuspendedTransaction(Dispatchers.IO) { block() }

    override suspend fun getAllOrdersByStoreId(storeId: Int): List<Order> {
        return dbQuery {
            Orders.select { Orders.storeId eq storeId }
                .map {
                    Order(
                        it[Orders.userId],
                        it[Orders.orderId],
                        it[Orders.storeId],
                        it[Orders.productId],
                        it[Orders.productQty]
                    )
                }
        }
    }

    /*
                Orders.select { Orders.orderId eq orderId }
                .map {
                    Order(
                        it[Orders.userId],
                        it[Orders.orderId],
                        it[Orders.storeId],
                        it[Orders.productId],
                        it[Orders.productQty]
                    )
                }
    */

    override suspend fun getOrderByOrderId(orderId: Int): List<ProductInOrderResponse> {
        return dbQuery {

            (ProductService.Products innerJoin Orders)
                .slice(
                    ProductService.Products.code,
                    ProductService.Products.article,
                    ProductService.Products.title,
                    Orders.productQty
                )
                .select { Orders.orderId eq orderId }
                .map {
                    ProductInOrderResponse(
                        it[ProductService.Products.code],
                        it[ProductService.Products.article],
                        it[ProductService.Products.title],
                        it[Orders.productQty]
                    )
                }
        }
    }

    override suspend fun getAllOrdersByUserId(userId: Int): Order? {
        TODO("Not yet implemented")
    }

    override suspend fun createOrder(userId: Int): Int {
        TODO("Not yet implemented")
    }

    override suspend fun updateOrderByOrderId(orderId: Int): Boolean {
        TODO("Not yet implemented")
    }

    override suspend fun deleteOrderByOrderId(orderId: Int): Boolean {
        TODO("Not yet implemented")
    }
}