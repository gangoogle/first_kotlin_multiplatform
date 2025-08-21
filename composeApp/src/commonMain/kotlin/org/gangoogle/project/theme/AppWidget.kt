package org.gangoogle.project.theme

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.core.annotation.ExperimentalVoyagerApi
import cafe.adriel.voyager.core.model.rememberNavigatorScreenModel
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.dokar.sonner.Toaster
import com.dokar.sonner.rememberToasterState
import org.gangoogle.project.AppShareViewModel
import org.gangoogle.project.appShareViewModel
import org.gangoogle.project.view.WeLoadingDialog

@OptIn(ExperimentalVoyagerApi::class)
@Composable
fun Screen.AppWidget(content: @Composable () -> Unit) {
    MaterialTheme {
        val navigator = LocalNavigator.currentOrThrow
        val appModel = navigator.rememberNavigatorScreenModel { AppShareViewModel() }
        val scope = rememberCoroutineScope()
        appShareViewModel = appModel
        WeLoadingDialog(appModel.state.showLoading)
        val toaster = rememberToasterState()
        LaunchedEffect(scope) {
            //每次页面切换都会创建新的scope
            appShareViewModel?.registerEffectMain(scope) {
                when (it) {
                    is AppShareViewModel.Effect.ShowToast -> {
                        toaster.show(it.msg)
                    }
                }
            }
        }
        Toaster(toaster)
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            content.invoke()
        }
    }

}