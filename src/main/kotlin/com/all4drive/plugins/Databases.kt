package com.all4drive.plugins

import com.all4drive.database.Db
import io.ktor.server.application.*

fun Application.configureDatabases() {
    Db.database
}
