package org.gangoogle.project.home

import androidx.compose.foundation.Image
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
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.viewmodel.compose.viewModel
import cafe.adriel.voyager.core.annotation.ExperimentalVoyagerApi
import cafe.adriel.voyager.core.lifecycle.LifecycleEffectOnce
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.core.screen.ScreenKey
import cafe.adriel.voyager.core.screen.uniqueScreenKey
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import coil3.ImageLoader
import coil3.compose.AsyncImage
import coil3.compose.LocalPlatformContext
import coil3.compose.rememberAsyncImagePainter
import coil3.request.ImageRequest
import coil3.svg.SvgDecoder
import com.dokar.sonner.Toaster
import com.dokar.sonner.rememberToasterState
import io.github.vinceglb.filekit.dialogs.compose.rememberFilePickerLauncher
import kotlinproject.composeapp.generated.resources.Res
import org.gangoogle.project.base.currentNavigator
import org.gangoogle.project.ext.color
import org.gangoogle.project.ext.obsCache
import org.gangoogle.project.getPlatform
import org.gangoogle.project.showToast
import org.gangoogle.project.theme.AppWidget
import org.gangoogle.project.view.SpaceH
import org.jetbrains.compose.ui.tooling.preview.Preview

class MainScreen : Screen {

    override val key: ScreenKey
        get() = "mainScreen"

    @OptIn(ExperimentalVoyagerApi::class)
    @Composable
    override fun Content() {
        val vm = viewModel { MainViewModel() }
        val platform = getPlatform()
        val nav = currentNavigator()
        var showDialogTest by false.obsCache
        val launcher = rememberFilePickerLauncher() { files ->
            // Handle picked files
        }
        LifecycleEffectOnce {
            println("AppShareViewModel MainScreen LifecycleEffectOnce ")
            vm.registerEffectMain {
                if (it is MainViewModel.Effect.GetPlatFormName) {
                    println("AppShareViewModel MainScreen GetPlatFormName ")
                    showToast("平台-> ${platform.name}")
                }
            }
        }
        TestDialog(showDialogTest) {
            showDialogTest = false
        }
        AppWidget {
            Column(
                modifier = Modifier.fillMaxSize(1f).background("#ececec".color),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(modifier = Modifier.fillMaxWidth(1f).background("#7db8fc".color)) {
                    Column(
                        modifier = Modifier.padding(WindowInsets.systemBars.asPaddingValues())
                            .fillMaxWidth(1f)
                    ) {}
                }
                AsyncImage(
                    model = "https://resources.jetbrains.com/help/img/kotlin-multiplatform-dev/jb-kotlin-multiplatform-logo.svg",
                    contentDescription = "",
                    modifier = Modifier.size(width = 100.dp, height = 50.dp)
                )
                Text(text = "${KotlinVersion.CURRENT} ")
                Button(onClick = {
                    nav.push(AboutUsScreen(type = "main-200"))
                }) {
                    Text("页面跳转->关于我们")
                }
                Button(onClick = {
                    vm.sendEffect(MainViewModel.Effect.GetPlatFormName)
                }) {
                    Text("显示平台名称")
                }
                Button(onClick = {
                    vm.requestHotKey()
                }) {
                    Text("ktor请求")
                }
                Row(
                    modifier = Modifier.fillMaxWidth(1f),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Button(onClick = {
                        vm.insertAppCache()
                    }) {
                        Text("键值对存储-> ${vm.state.cacheName}")
                    }
                }
                Button(onClick = {
                    showDialogTest = true
                }) {
                    Text("显示弹窗")
                }
                if (vm.state.qrCode != null) {
                    AsyncImage(
                        model = vm.state.qrCode,
                        modifier = Modifier.size(80.dp),
                        contentDescription = ""
                    )
                }
                Button(onClick = {
                    launcher.launch()
                }) { Text("文件选择") }
                Column(modifier = Modifier.weight(1f)) {
                    ListBody()
                }

            }
        }
    }


    @Composable
    fun TestDialog(show: Boolean, onDismiss: () -> Unit) {
        println("showDialogTest ${show}")
        if (show) {
            Dialog(onDismissRequest = { onDismiss.invoke() }) {
                Card(colors = CardDefaults.cardColors(containerColor = Color.White)) {
                    Column(
                        modifier = Modifier.fillMaxWidth(1f).padding(30.dp),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text("弹窗测试2")
                        SpaceH(50.dp)
                        Button(onClick = {
                            onDismiss.invoke()
                        }) {
                            Text("关闭")
                        }
                    }
                }
            }
        }
    }

    @Composable
    fun ListBody() {
        val vm = viewModel { MainViewModel() }
        LazyColumn(modifier = Modifier.fillMaxSize(1f)) {
            items(vm.state.list.size) {
                Row(
                    modifier = Modifier.fillMaxWidth(1f).background("#f3f1f3".color).padding(5.dp)
                ) {
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

@Preview
@Composable
fun previewMain() {
    MainScreen().Content()
}
