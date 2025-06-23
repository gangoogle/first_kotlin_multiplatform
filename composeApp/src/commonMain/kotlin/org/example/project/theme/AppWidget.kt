package org.example.project.theme

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.core.model.rememberNavigatorScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import org.example.project.AppShareViewModel
import org.example.project.appShareViewModel
import org.example.project.view.WeLoadingDialog

@Composable
fun AppWidget(content: @Composable () -> Unit) {

    MaterialTheme {
        val navigator = LocalNavigator.currentOrThrow
        val appModel = navigator.rememberNavigatorScreenModel { AppShareViewModel() }
        appShareViewModel = appModel
        WeLoadingDialog(appModel.state.showLoading)
        Column(
            modifier = Modifier
                .safeContentPadding()
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            content.invoke()
        }
    }

}