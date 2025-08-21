package org.gangoogle.project.base

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.currentOrThrow

@Composable
fun currentNavigator(): Navigator {
    return  LocalNavigator.currentOrThrow
}