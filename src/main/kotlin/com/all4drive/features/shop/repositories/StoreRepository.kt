package com.all4drive.features.shop.repositories

import com.all4drive.features.shop.models.store.Store

interface StoreRepository {

    suspend fun getAllStores(): List<Store>

    suspend fun getStoreByTitle(title: String): Store?

    suspend fun createStore(store: Store): Int

    suspend fun deleteStore(id: Int): Int
}