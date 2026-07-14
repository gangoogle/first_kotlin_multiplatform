package org.gangoogle.project.home

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import cafe.adriel.voyager.core.model.screenModelScope
import kotlin.random.Random
import org.gangoogle.project.base.BaseEffect
import org.gangoogle.project.base.BaseScreenModel
import org.gangoogle.project.base.BaseState
import org.gangoogle.project.data.bean.TopWords
import org.gangoogle.project.data.cache.AppCache
import org.gangoogle.project.ext.launchMain
import org.gangoogle.project.ext.listObs
import org.gangoogle.project.ext.obs
import org.gangoogle.project.ext.withIO
import org.gangoogle.project.hideLoading
import org.gangoogle.project.net.ApiServer
import org.gangoogle.project.net.scopeNetLife
import org.gangoogle.project.showLoading
import org.gangoogle.project.showToast
import qrcode.QRCode
import qrcode.color.Colors

class MainScreenModel : BaseScreenModel<MainScreenModel.State, MainScreenModel.Effect>(State()) {

    @Immutable
    class State : BaseState() {
        var list = listObs<TopWords>()
        var cacheName by obs("")
        var qrCode by obs<ByteArray?>(null)
    }

    sealed class Effect : BaseEffect() {
        data object GetPlatFormName : Effect()
    }

    init {
        state.cacheName = AppCache.userName
        createQrCode()
    }

    fun insertAppCache() {
        AppCache.userName = "${Random.nextInt(1000)}"
        state.cacheName = AppCache.userName
    }

    fun requestHotKey() {
        showLoading()
        screenModelScope.scopeNetLife(
            request = {
                val hotKey = ApiServer.getHotKey().await()
                state.list.clear()
                state.list.addAll(hotKey)
            },
            onFinish = { error ->
                hideLoading()
                error?.let { showToast("onError:${it.message}") }
            },
        )
    }

    private fun createQrCode() {
        screenModelScope.launchMain {
            val graphics = withIO {
                QRCode.ofSquares().withColor(Colors.BLACK).build("Hello World").render()
            }
            state.qrCode = graphics.getBytes()
            println("qrcode create ${state.qrCode?.size}")
        }
    }
}
