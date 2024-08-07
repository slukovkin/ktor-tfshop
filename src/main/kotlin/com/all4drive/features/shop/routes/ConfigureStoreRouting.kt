package com.all4drive.features.shop.routes

import com.all4drive.features.shop.models.store.Store
import com.all4drive.features.shop.services.StoreService
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.storeRouting() {

    val storeService = StoreService()
    route("/api/store") {

        get {
            val stores = storeService.getAllStores()
            call.respond(HttpStatusCode.OK, stores)
        }

        get("{title}") {
            val title = call.parameters["title"] ?: throw IllegalArgumentException("TITLE")
            val store = storeService.getStoreByTitle(title)
            if (store != null)
                call.respond(HttpStatusCode.OK, store)
            else
                call.respond(HttpStatusCode.NotFound)
        }

        post {
            val store = call.receive<Store>()
            if (storeService.createStore(store) != 0)
                call.respond(HttpStatusCode.OK, "Успешно создан")
            else
                call.respond(HttpStatusCode.Conflict, "Склад уже существует в БД")
        }

        delete("{id}") {
            val id = call.parameters["id"]?.toInt() ?: throw IllegalArgumentException("TITLE")
            if (storeService.deleteStore(id) != 0)
                call.respond(HttpStatusCode.OK, "Склад успешно удален из БД")
            else
                call.respond(HttpStatusCode.NotFound, "Склад не найден в БД")
        }
    }
}

fun Application.configureStoreRouting() {
    routing {
        storeRouting()
    }
}