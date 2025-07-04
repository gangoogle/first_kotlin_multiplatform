package org.example.project

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import org.example.project.ext.launchIO
import org.example.project.ext.launchMain
import org.example.project.ext.obs

/**
 * app级别共享数据
 */
@Immutable
class AppShareViewModel : ScreenModel {
    private val _effect = MutableSharedFlow<Effect>(replay = 0)
    private val effect = _effect.asSharedFlow()
    val state = State()


    fun registerEffectIO(scope: CoroutineScope, block: suspend (Effect) -> Unit) {
        scope.launchIO {
            effect.collect(block)
        }
    }

    fun registerEffectMain(scope: CoroutineScope, block: suspend (Effect) -> Unit) {
        scope.launchMain {
            effect.collect(block)
        }
    }


    //发送事件
    fun sendEffect(event: Effect) {
        screenModelScope.launch {
            _effect.emit(event)
        }
    }

    @Immutable
    class State {
        var showLoading by false.obs
    }

    sealed class Effect {

    }

}

fun showLoading() {
    appShareViewModel?.apply { state.showLoading = true }
}

fun hideLoading() {
    appShareViewModel?.apply { state.showLoading = false }

}

