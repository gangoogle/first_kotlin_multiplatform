package org.gangoogle.project.net

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.async
import kotlinx.coroutines.launch


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

fun ViewModel.scopeNetLife(
    dispatcher: CoroutineDispatcher = Dispatchers.Main,
    request: suspend CoroutineScope.() -> Unit,
    onFinish: (error: Throwable?) -> Unit = {},
) {
    this.viewModelScope.scopeNetLife(dispatcher, request, onFinish)
}

fun CoroutineScope.scopeNetLife(
    dispatcher: CoroutineDispatcher = Dispatchers.Main,
    request: suspend CoroutineScope.() -> Unit,
    onFinish: (error: Throwable?) -> Unit = {},
) {
    var throwable: Throwable? = null
    this.launch(dispatcher) {
        try {
            request.invoke(this)
        } catch (e: Throwable) {
            throwable = e
        } finally {
            onFinish.invoke(throwable)
        }
    }
}


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
