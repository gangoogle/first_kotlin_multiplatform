package org.example.project.home

import androidx.compose.runtime.Immutable
import cafe.adriel.voyager.core.model.screenModelScope
import com.sea.jetpack.base.BaseEffect
import com.sea.jetpack.base.BaseState
import org.example.project.appShareViewModel
import org.example.project.base.BaseComposeVM
import org.example.project.ext.listObs
import org.example.project.hideLoading
import org.example.project.net.ApiServer
import org.example.project.net.TopWords
import org.example.project.net.startRequestScope
import org.example.project.showLoading

class MainScreenModel : BaseComposeVM<MainScreenModel.State, MainScreenModel.Effect>(State()) {

    @Immutable
    class State : BaseState() {
        var list = listObs<TopWords>()
    }

    init {
        println("mainScreenModel init")
        initData()
    }

    sealed class Effect : BaseEffect() {
        data object SayHello : Effect()
    }


    private fun initData() {
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