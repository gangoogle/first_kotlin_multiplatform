package org.gangoogle.project.home

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import cafe.adriel.voyager.core.model.screenModelScope
import org.gangoogle.project.base.BaseEffect
import org.gangoogle.project.base.BaseState
import org.gangoogle.project.base.BaseComposeVM
import org.gangoogle.project.data.bean.TopWords
import org.gangoogle.project.data.cache.AppCache
import org.gangoogle.project.ext.listObs
import org.gangoogle.project.ext.obs
import org.gangoogle.project.hideLoading
import org.gangoogle.project.net.ApiServer
import org.gangoogle.project.net.startRequestScope
import org.gangoogle.project.showLoading
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