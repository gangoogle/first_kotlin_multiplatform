package org.example.project.base

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.sea.jetpack.base.BaseEffect
import com.sea.jetpack.base.BaseState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

open class BaseComposeVM<S : BaseState, T : BaseEffect>(val state: S) : ScreenModel {
    private val _baseEffect = MutableSharedFlow<T>(replay = 0)
    private val effect = _baseEffect.asSharedFlow()

    override fun onDispose() {
        super.onDispose()
    }

    fun registerEffectIO(block: suspend (T) -> Unit) {
        screenModelScope.launch(Dispatchers.IO) {
            effect.collect(block)
        }
    }

    fun registerEffectMain(block: suspend (T) -> Unit) {
        screenModelScope.launch(Dispatchers.Main) {
            effect.collect(block)
        }
    }

    //发送事件
    fun sendEffect(event: T) {
        screenModelScope.launch {
            _baseEffect.emit(event)
        }
    }
}