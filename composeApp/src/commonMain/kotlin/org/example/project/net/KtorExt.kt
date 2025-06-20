package org.example.project.net

import androidx.lifecycle.ViewModel
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import kotlinx.serialization.*
import kotlinx.serialization.json.*
import kotlin.reflect.KClass
import io.ktor.util.reflect.*
import io.ktor.client.plugins.contentnegotiation.*
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


suspend inline fun <reified T> HttpClient.getUnwrapped(url: String): T {
    return this.get(url).body()
}

suspend inline fun <reified T, reified Req> HttpClient.postUnwrapped(
    url: String,
    requestBody: Req
): T {
    return this.post(url) {
        contentType(ContentType.Application.Json)
        setBody(requestBody)
    }.body()
}

fun CoroutineScope.startRequestScope(
    request: suspend CoroutineScope.() -> Unit,
    onFinish: (error:Throwable?) -> Unit = {},
) {
    var throwable: Throwable? = null
    this.launch(Dispatchers.IO) {
        try {
            request.invoke(this)
        } catch (e: Throwable) {
            throwable = e
        } finally {
            withContext(Dispatchers.Main) {
                onFinish.invoke(throwable)
            }
        }
    }
}