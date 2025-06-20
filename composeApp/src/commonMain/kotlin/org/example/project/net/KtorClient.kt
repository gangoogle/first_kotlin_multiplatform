package org.example.project.net

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

object KtorClient {
    lateinit var client: HttpClient

    fun init() {
        client = HttpClient() {
            defaultRequest {
                url.takeFrom(UrlConfig.baseUrl)
                header("WarehouseCode", "YDSHA")
            }
            install(ContentNegotiation) {
                register(ContentType.Application.Json, AutoUnwrapKotlinxConverter())
            }

        }
    }
}

object UrlConfig {
    val baseUrl = "https://www.wanandroid.com//"
    val hotKey = "hotkey/json"
}

object ApiServer {

    inline fun <reified T> ktorGet(
        path: String,
        noinline block: suspend () -> T = {
            KtorClient.client.get(path).body<T>()
        }
    ): suspend () -> Deferred<T> = {
        CoroutineScope(Dispatchers.IO).async { block() }
    }

    inline fun <reified T> ktorPost(
        path: String,
        requestBody: Any,
        noinline block: suspend () -> T = {
            KtorClient.client.post {
                url.takeFrom(path)
                setBody(requestBody)
            }.body<T>()
        }
    ): suspend () -> Deferred<T> = {
        CoroutineScope(Dispatchers.IO).async { block() }
    }


    suspend fun getTestUrl() = ktorGet<List<TopWords>>(UrlConfig.hotKey)()
    

}