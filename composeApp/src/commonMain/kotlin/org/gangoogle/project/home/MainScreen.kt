package org.gangoogle.project.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.annotation.ExperimentalVoyagerApi
import cafe.adriel.voyager.core.lifecycle.LifecycleEffectOnce
import cafe.adriel.voyager.core.model.rememberScreenModel
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import org.gangoogle.project.ext.MyPreview
import org.gangoogle.project.ext.color
import org.gangoogle.project.getPlatform
import org.gangoogle.project.theme.AppWidget

class MainScreen : Screen {
    @OptIn(ExperimentalVoyagerApi::class)
    @Composable
    override fun Content() {
        val vm = rememberScreenModel { MainScreenModel() }
        val platform = getPlatform()
        val nav = LocalNavigator.currentOrThrow
        LifecycleEffectOnce {
            println("main screen launchedEffect")
            vm.registerEffectMain {
                if (it is MainScreenModel.Effect.GetPlatFormName) {
                    println("平台-> ${platform.name}")
                }
            }
        }
        AppWidget {
            Column(
                modifier = Modifier.fillMaxSize(1f).background("#f6f8fa".color),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Button(onClick = {
                    nav.push(AboutUsScreen(type = "main-200"))
                }) {
                    Text("goto about")
                }
                Button(onClick = {
                    vm.sendEffect(MainScreenModel.Effect.GetPlatFormName)
                }) {
                    Text("获取平台名称")
                }
                Button(onClick = {
                    vm.requestHotKey()
                }) {
                    Text("url request test")
                }
                Text("cacheUserName: ${vm.state.cacheName}")
                Button(onClick = {
                    vm.insertAppCache()
                }) {
                    Text("App Cache Insert")
                }
                Column(modifier = Modifier.weight(1f)) {
                    ListBody()
                }
            }
        }
    }

    @Composable
    fun ListBody() {
        val vm = rememberScreenModel { MainScreenModel() }
        LazyColumn(modifier = Modifier.fillMaxSize(1f)) {
            items(vm.state.list.size) {
                Row(modifier = Modifier.fillMaxWidth(1f).background("#28b4ff".color)) {
                    Text(vm.state.list[it].name ?: "")
                    Spacer(modifier = Modifier.width(5.dp))
                    Text(vm.state.list[it].link ?: "")
                    Spacer(modifier = Modifier.width(5.dp))
                    Text(vm.state.list[it].order.toString())
                }
                HorizontalDivider()
            }
        }
    }

}

@MyPreview
@Composable
private fun PreviewMainScreen103() {
    MainScreen().ListBody()
}