package com.all4drive.database

import org.jetbrains.exposed.sql.Database

class Db {
    companion object {
        val database = Database.connect(
            url = "jdbc:mysql://localhost:3306/tfshop?useUnicode=true&serverTimezone=UTC&useSSL=false",
            user = "root",
            driver = "com.mysql.cj.jdbc.Driver",
            password = "86818682"
        )
    }
}