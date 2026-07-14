package org.gangoogle.project.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import cafe.adriel.voyager.core.annotation.ExperimentalVoyagerApi
import cafe.adriel.voyager.core.lifecycle.LifecycleEffectOnce
import cafe.adriel.voyager.core.model.rememberScreenModel
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.core.screen.ScreenKey
import coil3.compose.AsyncImage
import io.github.vinceglb.filekit.dialogs.compose.rememberFilePickerLauncher
import org.gangoogle.project.base.currentNavigator
import org.gangoogle.project.ext.color
import org.gangoogle.project.ext.obsCache
import org.gangoogle.project.getPlatform
import org.gangoogle.project.showToast
import org.gangoogle.project.theme.AppWidget
import org.gangoogle.project.view.SpaceH
import org.jetbrains.compose.ui.tooling.preview.Preview

class MainScreen : Screen {

    override val key: ScreenKey = "mainScreen"

    @OptIn(ExperimentalVoyagerApi::class)
    @Composable
    override fun Content() {
        val model = rememberScreenModel { MainScreenModel() }
        val platform = getPlatform()
        val navigator = currentNavigator()
        val filePicker = rememberFilePickerLauncher { _ -> }

        LifecycleEffectOnce {
            model.registerEffectMain { effect ->
                if (effect is MainScreenModel.Effect.GetPlatFormName) {
                    showToast("平台-> ${platform.name}")
                }
            }
        }

        AppWidget {
            MainContent(
                state = model.state,
                logoModel = LOGO_URL,
                onOpenAboutUs = { navigator.push(AboutUsScreen(type = "main-200")) },
                onShowPlatformName = {
                    model.sendEffect(MainScreenModel.Effect.GetPlatFormName)
                },
                onRequestHotKey = model::requestHotKey,
                onInsertCache = model::insertAppCache,
                onPickFile = filePicker::launch,
            )
        }
    }
}

@Composable
private fun MainContent(
    state: MainScreenModel.State,
    logoModel: Any?,
    onOpenAboutUs: () -> Unit,
    onShowPlatformName: () -> Unit,
    onRequestHotKey: () -> Unit,
    onInsertCache: () -> Unit,
    onPickFile: () -> Unit,
) {
    var showDialog by false.obsCache

    TestDialog(showDialog) { showDialog = false }
    Column(
        modifier = Modifier.fillMaxSize().background("#ececec".color),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Box(modifier = Modifier.fillMaxWidth().background("#7db8fc".color)) {
            Column(
                modifier = Modifier.padding(WindowInsets.systemBars.asPaddingValues())
                    .fillMaxWidth(),
            ) {}
        }
        AsyncImage(
            model = logoModel,
            contentDescription = "",
            modifier = Modifier.size(width = 100.dp, height = 50.dp),
        )
        Text(text = "${KotlinVersion.CURRENT} ")
        Button(onClick = onOpenAboutUs) { Text("页面跳转->关于我们") }
        Button(onClick = onShowPlatformName) { Text("显示平台名称") }
        Button(onClick = onRequestHotKey) { Text("ktor请求") }
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
        ) {
            Button(onClick = onInsertCache) {
                Text("键值对存储-> ${state.cacheName}")
            }
        }
        Button(onClick = { showDialog = true }) { Text("显示弹窗") }
        state.qrCode?.let { qrCode ->
            AsyncImage(
                model = qrCode,
                modifier = Modifier.size(80.dp),
                contentDescription = "",
            )
        }
        Button(onClick = onPickFile) { Text("文件选择") }
        Column(modifier = Modifier.weight(1f)) {
            MainList(state)
        }
    }
}

@Composable
private fun TestDialog(show: Boolean, onDismiss: () -> Unit) {
    if (show) {
        Dialog(onDismissRequest = onDismiss) {
            Card(colors = CardDefaults.cardColors(containerColor = Color.White)) {
                Column(
                    modifier = Modifier.fillMaxWidth().padding(30.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Text("弹窗测试2")
                    SpaceH(50.dp)
                    Button(onClick = onDismiss) { Text("关闭") }
                }
            }
        }
    }
}

@Composable
private fun MainList(state: MainScreenModel.State) {
    LazyColumn(modifier = Modifier.fillMaxSize()) {
        items(state.list.size) { index ->
            val item = state.list[index]
            Row(
                modifier = Modifier.fillMaxWidth().background("#f3f1f3".color).padding(5.dp),
            ) {
                Text(item.name.orEmpty())
                Spacer(modifier = Modifier.width(5.dp))
                Text(item.link.orEmpty())
                Spacer(modifier = Modifier.width(5.dp))
                Text(item.order.toString())
            }
            HorizontalDivider()
        }
    }
}

@Preview
@Composable
private fun PreviewMainContent() {
    MaterialTheme {
        MainContent(
            state = MainScreenModel.State().apply { cacheName = "Preview 用户" },
            logoModel = null,
            onOpenAboutUs = {},
            onShowPlatformName = {},
            onRequestHotKey = {},
            onInsertCache = {},
            onPickFile = {},
        )
    }
}

private const val LOGO_URL =
    "https://resources.jetbrains.com/help/img/kotlin-multiplatform-dev/jb-kotlin-multiplatform-logo.svg"
