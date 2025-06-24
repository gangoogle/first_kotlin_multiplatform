package org.gangoogle.project.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator

data class AboutUsScreen(val type:String) : Screen {

    @Composable
    override fun Content() {
        val nav = LocalNavigator.current
        Column(
            modifier = Modifier.fillMaxSize(1f), verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("AboutUsScreen->params:$type")
            Button(onClick = {
                nav?.pop()
            }){
                Text("Go to MainScreen")
            }
        }
    }


}