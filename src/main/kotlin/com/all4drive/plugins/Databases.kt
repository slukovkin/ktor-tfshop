package com.all4drive.plugins

import com.all4drive.database.Db
import io.ktor.server.application.*
import io.ktor.server.routing.*

fun Application.configureDatabases() {
    val database = Db.database
    routing {
    }
}
