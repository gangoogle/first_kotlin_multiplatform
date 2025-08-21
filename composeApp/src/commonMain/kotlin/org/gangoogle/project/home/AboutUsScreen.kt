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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import cafe.adriel.voyager.core.annotation.ExperimentalVoyagerApi
import cafe.adriel.voyager.core.lifecycle.LifecycleEffectOnce
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.core.screen.ScreenKey
import cafe.adriel.voyager.navigator.LocalNavigator
import org.gangoogle.project.ext.color
import org.gangoogle.project.showToast
import org.gangoogle.project.theme.AppWidget

data class AboutUsScreen(val type: String) : Screen {

    override val key: ScreenKey
        get() = "AboutUsScreen"

    @OptIn(ExperimentalVoyagerApi::class)
    @Composable
    override fun Content() {
        val vm = viewModel { AboutUsViewModel() }
        val nav = LocalNavigator.current
        LifecycleEffectOnce {
            println("AppShareViewModel AboutUsScreen LifecycleEffectOnce ")
            vm.queryDb()
        }
        AppWidget {
            Column(
                modifier = Modifier.fillMaxSize(1f).background("#ffffff".color),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text("传递参数:$type")
                Button(onClick = {
                    nav?.pop()
                }) {
                    Text("Go to MainScreen")
                }
                Button(onClick = {
                    vm.insertDb()
                }) {
                    Text("add sqlite")
                }
                Button(onClick = {
                    showToast("test!!!")
                }) {
                    Text("Toast")
                }
                Column(modifier = Modifier.fillMaxWidth(1f).weight(1f)) {
                    LazyColumn(modifier = Modifier.fillMaxSize(1f)) {
                        items(vm.state.userList.size) {
                            Box(modifier = Modifier.padding(10.dp)) {
                                Text(text = vm.state.userList[it].serviceIp)
                            }
                        }
                    }
                }
            }
        }
    }

}