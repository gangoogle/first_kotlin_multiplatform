package org.gangoogle.project
import androidx.compose.runtime.*
import cafe.adriel.voyager.navigator.Navigator
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.gangoogle.project.home.MainScreen
import org.gangoogle.project.net.KtorClient

@Composable
@Preview
fun App() {
    LaunchedEffect(Unit) {
        println("App LaunchedEffect")
        KtorClient.init()
    }
    Navigator(MainScreen())
}

var appShareViewModel:AppShareViewModel?=null

