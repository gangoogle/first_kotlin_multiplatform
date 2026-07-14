package org.gangoogle.project.base

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

open class BaseScreenModel<S : BaseState, T : BaseEffect>(val state: S) : ScreenModel {
    private val baseEffect = MutableSharedFlow<T>(replay = 0)
    private val effect = baseEffect.asSharedFlow()

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

    fun sendEffect(event: T) {
        screenModelScope.launch {
            baseEffect.emit(event)
        }
    }
}
