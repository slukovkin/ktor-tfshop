package com.all4drive.database

import com.all4drive.config.DatabaseConfig
import org.jetbrains.exposed.sql.Database

class Db {
    companion object {
        private val config = DatabaseConfig.dbconfig
        val database = Database.connect(
            url = config.url,
            user = config.user,
            driver = config.driver,
            password = config.password
        )
    }
}