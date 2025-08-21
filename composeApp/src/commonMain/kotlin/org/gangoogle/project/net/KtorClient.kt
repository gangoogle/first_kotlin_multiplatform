package org.gangoogle.project.net

import AutoUnwrapKotlinxConverter
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.takeFrom
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.async
import org.gangoogle.project.data.bean.TopWords

object KtorClient {
    lateinit var client: HttpClient

    fun init() {
        client = HttpClient() {
            defaultRequest {
                url.takeFrom(UrlDispatcher.getBaseUrl())
                header("WarehouseCode", "YDSHA")
            }
            install(ContentNegotiation) {
                register(ContentType.Application.Json, AutoUnwrapKotlinxConverter())
            }

        }
    }
}


