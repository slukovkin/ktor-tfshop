package com.all4drive.features.store.routes

import com.all4drive.database.Db
import com.all4drive.features.store.model.Store
import com.all4drive.features.store.service.StoreService
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.storeRouting() {

    val storeService = StoreService(Db.database)
    route("/api/store") {
        post {
            val store = call.receive<Store>()
            storeService.createStore(store)
            call.respond(HttpStatusCode.OK, "Успешно создан")
        }

        delete("{id}") {
            val id = call.parameters["id"]?.toInt() ?: throw IllegalArgumentException("TITLE")
            if (storeService.deleteStore(id) != 0)
                call.respond(HttpStatusCode.OK)
            else
                call.respond(HttpStatusCode.NotFound)
        }
    }
}

fun Application.configureStoreRouting() {
    routing {
        storeRouting()
    }
}