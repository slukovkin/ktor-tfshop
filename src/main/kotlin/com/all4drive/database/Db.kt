package com.all4drive.database

import com.all4drive.config.DatabaseConfig
import org.jetbrains.exposed.sql.Database

object Db {
    val database = Database.connect(
        url = DatabaseConfig.url,
        user = DatabaseConfig.user,
        driver = DatabaseConfig.driver,
        password = DatabaseConfig.password
    )
}