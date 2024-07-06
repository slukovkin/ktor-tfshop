package com.all4drive.database

import com.all4drive.config.DatabaseConfig
import org.jetbrains.exposed.sql.Database

object Db {
    val database = Database.connect(
        url = DatabaseConfig.URL,
        user = DatabaseConfig.USER,
        driver = DatabaseConfig.DRIVER,
        password = DatabaseConfig.PASSWORD
    )
}