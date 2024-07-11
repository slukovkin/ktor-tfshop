package com.all4drive.features.shop.repositories

import com.all4drive.features.shop.models.product.Product
import com.all4drive.features.shop.models.product.ProductInStoreRespond

interface ProductRepository {
    suspend fun getProductById(id: Int): Product?

    suspend fun insertProductToStore(storeId: Int, productId: Int, qty: Double, priceIn: Double): Int

    suspend fun updateProductOnStore(productId: Int, qty: Double, priceIn: Double)

    suspend fun searchProductOnStoreById(id: Int, storeId: Int): Boolean

    suspend fun getProductsFromStore(storeId: Int): List<ProductInStoreRespond>

    suspend fun getAllProducts(): List<Product>

    suspend fun getProductByCode(code: Int): Product?

    suspend fun getProductsByCross(cross: Int): List<Product>

    suspend fun create(product: Product): Int

    suspend fun deleteProductByCode(code: Int): Int

    suspend fun updateProductByCode(code: Int, product: Product): Int
}