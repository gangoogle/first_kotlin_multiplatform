package org.gangoogle.project.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.annotation.ExperimentalVoyagerApi
import cafe.adriel.voyager.core.lifecycle.LifecycleEffectOnce
import cafe.adriel.voyager.core.model.rememberScreenModel
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.core.screen.ScreenKey
import cafe.adriel.voyager.navigator.LocalNavigator
import org.gangoogle.project.ext.color
import org.gangoogle.project.showToast
import org.gangoogle.project.theme.AppWidget
import org.jetbrains.compose.ui.tooling.preview.Preview

data class AboutUsScreen(val type: String) : Screen {

    override val key: ScreenKey = "AboutUsScreen"

    @OptIn(ExperimentalVoyagerApi::class)
    @Composable
    override fun Content() {
        val model = rememberScreenModel { AboutUsScreenModel() }
        val navigator = LocalNavigator.current

        LifecycleEffectOnce {
            model.queryDb()
        }

        AppWidget {
            AboutUsContent(
                type = type,
                state = model.state,
                onBack = { navigator?.pop() },
                onInsert = model::insertDb,
                onShowToast = { showToast("test!!!") },
            )
        }
    }
}

@Composable
private fun AboutUsContent(
    type: String,
    state: AboutUsScreenModel.State,
    onBack: () -> Unit,
    onInsert: () -> Unit,
    onShowToast: () -> Unit,
) {
    Column(
        modifier = Modifier.fillMaxSize().background("#ffffff".color),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text("传递参数:$type")
        Button(onClick = onBack) { Text("Go to MainScreen") }
        Button(onClick = onInsert) { Text("add sqlite") }
        Button(onClick = onShowToast) { Text("Toast") }
        Column(modifier = Modifier.fillMaxWidth().weight(1f)) {
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                items(state.userList.size) { index ->
                    Box(modifier = Modifier.padding(10.dp)) {
                        Text(text = state.userList[index].serviceIp)
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun PreviewAboutUsContent() {
    MaterialTheme {
        AboutUsContent(
            type = "Preview",
            state = AboutUsScreenModel.State(),
            onBack = {},
            onInsert = {},
            onShowToast = {},
        )
    }
}
