package org.gangoogle.project

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.core.annotation.ExperimentalVoyagerApi
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.transitions.SlideTransition
import org.gangoogle.project.home.MainScreen
import org.gangoogle.project.net.KtorClient

/**
 * MyApplication
 */
@OptIn(ExperimentalVoyagerApi::class)
@Composable
fun App() {
    LaunchedEffect(Unit) {
        println("App LaunchedEffect")
        KtorClient.init()
    }
    val snackBarHostState = remember { SnackbarHostState() }
    LaunchedEffect(snackBarHostState) {
        collectAppEffects { effect ->
            when (effect) {
                is AppShareViewModel.Effect.ShowToast -> {
                    snackBarHostState.showSnackbar(effect.msg)
                }
            }
        }
    }
    MaterialTheme {
        Box(modifier = Modifier.fillMaxSize()) {
            Navigator(MainScreen()) { nav ->
                SlideTransition(nav, disposeScreenAfterTransitionEnd = true)
            }
            SnackbarHost(
                hostState = snackBarHostState,
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .safeDrawingPadding(),
            )
        }
    }
}

var appShareViewModel: AppShareViewModel? = null
