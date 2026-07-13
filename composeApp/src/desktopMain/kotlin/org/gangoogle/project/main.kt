package org.gangoogle.project

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application

/**
 * 主函数
 */
fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "KotlinProject",
    ) {
        App()
    }
}