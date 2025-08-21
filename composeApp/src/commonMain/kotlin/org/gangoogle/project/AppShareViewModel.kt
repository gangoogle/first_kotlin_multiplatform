package org.gangoogle.project

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import org.gangoogle.project.AppShareViewModel.Effect
import org.gangoogle.project.ext.launchIO
import org.gangoogle.project.ext.launchMain
import org.gangoogle.project.ext.obs

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
            println("AppShareViewModel  sendEffect ->${event}")
            _effect.emit(event)
        }
    }

    @Immutable
    class State {
        var showLoading by false.obs
    }

    sealed class Effect {
        data class ShowToast(val msg: String) : Effect()
    }

}

fun showLoading() {
    appShareViewModel?.apply { state.showLoading = true }
}

fun hideLoading() {
    appShareViewModel?.apply { state.showLoading = false }
}

fun showToast(msg: String) {
    appShareViewModel?.sendEffect(Effect.ShowToast(msg))
}
