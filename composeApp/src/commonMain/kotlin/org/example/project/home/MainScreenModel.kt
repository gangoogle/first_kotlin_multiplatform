package org.example.project.home

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import cafe.adriel.voyager.core.model.screenModelScope
import com.sea.jetpack.base.BaseEffect
import com.sea.jetpack.base.BaseState
import org.example.project.base.BaseComposeVM
import org.example.project.data.bean.TopWords
import org.example.project.data.cache.AppCache
import org.example.project.ext.listObs
import org.example.project.ext.obs
import org.example.project.hideLoading
import org.example.project.net.ApiServer
import org.example.project.net.startRequestScope
import org.example.project.showLoading
import kotlin.random.Random

class MainScreenModel : BaseComposeVM<MainScreenModel.State, MainScreenModel.Effect>(State()) {

    @Immutable
    class State : BaseState() {
        var list = listObs<TopWords>()
        var cacheName by obs<String>("")
    }

    init {
        println("mainScreenModel init")
        initData()
    }

    sealed class Effect : BaseEffect() {
        data object GetPlatFormName : Effect()
    }


    private fun initData() {
        state.cacheName = AppCache.userName
    }

    fun insertAppCache() {
        AppCache.userName = "my name ${Random.nextInt(1000)}"
        state.cacheName = AppCache.userName
    }

    fun requestHotKey() {
        showLoading()
        screenModelScope.startRequestScope(request = {
            val hotKey = ApiServer.getTestUrl().await()
            state.list.clear()
            state.list.addAll(hotKey)
        }, onFinish = { error ->
            hideLoading()
            error?.let {
                println("onError:" + it.message)
            }
        })
    }

}