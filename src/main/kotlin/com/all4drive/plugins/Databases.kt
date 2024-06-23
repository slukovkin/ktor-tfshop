package com.all4drive.plugins

import io.ktor.server.application.*
import io.ktor.server.routing.*
import org.jetbrains.exposed.sql.Database

fun Application.configureDatabases() {
    val database = Database.connect(
        url = "jdbc:mysql://localhost:3306/tfshop?useUnicode=true&serverTimezone=UTC&useSSL=false",
        user = "root",
        driver = "com.mysql.cj.jdbc.Driver",
        password = "86818682"
    )
    routing {
    }
}
