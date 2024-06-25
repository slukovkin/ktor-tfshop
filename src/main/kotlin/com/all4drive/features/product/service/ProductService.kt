package com.all4drive.features.product.service

import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.transactions.transaction

class ProductService(db: Database) {

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
        transaction(db) {
            SchemaUtils.create(Products)
        }
    }
}