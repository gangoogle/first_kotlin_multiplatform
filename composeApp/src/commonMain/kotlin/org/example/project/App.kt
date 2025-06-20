package org.example.project

import androidx.compose.runtime.*
import cafe.adriel.voyager.navigator.Navigator
import org.jetbrains.compose.ui.tooling.preview.Preview

import org.example.project.home.MainScreen
import org.example.project.net.KtorClient

@Composable
@Preview
fun App() {
    LaunchedEffect(Unit) {
        KtorClient.init()
    }
    Navigator(MainScreen())
}

var appShareViewModel:AppShareViewModel?=null

