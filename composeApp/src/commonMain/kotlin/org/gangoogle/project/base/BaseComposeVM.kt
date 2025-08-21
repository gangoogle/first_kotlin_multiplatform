package org.gangoogle.project.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

open class BaseComposeVM<S : BaseState, T : BaseEffect>(val state: S) : ViewModel() {
    private val _baseEffect = MutableSharedFlow<T>(replay = 0)
    private val effect = _baseEffect.asSharedFlow()



    fun registerEffectIO(block: suspend (T) -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            effect.collect(block)
        }
    }

    fun registerEffectMain(block: suspend (T) -> Unit) {
        viewModelScope.launch(Dispatchers.Main) {
            effect.collect(block)
        }
    }

    //发送事件
    fun sendEffect(event: T) {
        viewModelScope.launch {
            _baseEffect.emit(event)
        }
    }
}