package org.gangoogle.project

import androidx.compose.runtime.*
import cafe.adriel.voyager.core.annotation.ExperimentalVoyagerApi
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.NavigatorDisposeBehavior
import cafe.adriel.voyager.transitions.SlideTransition
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.gangoogle.project.home.MainScreen
import org.gangoogle.project.net.KtorClient

@OptIn(ExperimentalVoyagerApi::class)
@Composable
@Preview
fun App() {
    LaunchedEffect(Unit) {
        println("App LaunchedEffect")
        KtorClient.init()
    }
    Navigator(
        MainScreen(),
        disposeBehavior = NavigatorDisposeBehavior(disposeSteps = false)
    ) { nav ->
        SlideTransition(nav, disposeScreenAfterTransitionEnd = true)
    }
}

var appShareViewModel: AppShareViewModel? = null

