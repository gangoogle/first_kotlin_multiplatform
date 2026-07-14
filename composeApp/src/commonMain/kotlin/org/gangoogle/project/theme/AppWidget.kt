package org.gangoogle.project.theme

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.core.annotation.ExperimentalVoyagerApi
import cafe.adriel.voyager.core.model.rememberNavigatorScreenModel
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import org.gangoogle.project.AppShareViewModel
import org.gangoogle.project.appShareViewModel
import org.gangoogle.project.view.WeLoadingDialog

/**
 * App插件
 */
@OptIn(ExperimentalVoyagerApi::class)
@Composable
fun Screen.AppWidget(content: @Composable () -> Unit) {
    MaterialTheme {
        val navigator = LocalNavigator.currentOrThrow
        val appModel = navigator.rememberNavigatorScreenModel { AppShareViewModel() }
        appShareViewModel = appModel
        WeLoadingDialog(appModel.state.showLoading)
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            content.invoke()
        }
    }

}
