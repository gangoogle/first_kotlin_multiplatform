package org.example.project.home

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
import org.example.project.ext.color
import org.example.project.getPlatform
import org.example.project.theme.AppWidget

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
                if (it is MainScreenModel.Effect.SayHello) {
                    println("main screen SayHello -> $it")
                }
            }
        }
        AppWidget {
            Column(
                modifier = Modifier.fillMaxSize(1f),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text("平台类型 -> ${platform.name}")
                Button(onClick = {
                    nav.push(AboutUsScreen(type = "main-200"))
                }) {
                    Text("goto about")
                }
                Button(onClick = {
                    vm.sendEffect(MainScreenModel.Effect.SayHello)
                }) {
                    Text("say hello effect")
                }
                Button(onClick = {
                    vm.requestHotKey()
                }) {
                    Text("url request test")
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
                Row(modifier = Modifier.fillMaxWidth(1f).background("#999999".color)) {
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
