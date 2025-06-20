package org.example.project.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog

/**
 * 原型进度条弹窗
 */
@Composable
fun WeLoadingDialog(
    visible: Boolean,
    title: String="",
) {
    if (visible) {
        Dialog(onDismissRequest = {

        }) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Column(modifier = Modifier.size(40.dp)) {
                    ProgressBarCircle()
                }
                Spacer(modifier = Modifier.height(10.dp))
                Text(title, fontSize = 18.sp, color = Color.White)
            }
        }
    }
}


@Stable
interface WeLoadingDialogState {
    /**
     * 是否显示
     */
    val visible: Boolean

    /**
     * 显示提示框
     */
    fun show(
        title: String
    )

    /**
     * 隐藏提示框
     */
    fun hide()
}

@Composable
fun rememberLoadingDialogState(): WeLoadingDialogState {
    val state = remember { WeLoadingDialogStateImpl() }
    state.props?.let { props ->
        WeLoadingDialog(
            visible = state.visible,
            title = props.title,
        )
    }

    return state
}

private class WeLoadingDialogStateImpl : WeLoadingDialogState {

    override var visible by mutableStateOf(false)

    var props by mutableStateOf<WeLoadingStateProps?>(null)
        private set

    override fun show(title: String) {
        props = WeLoadingStateProps(title)
        visible = true
    }

    override fun hide() {
        visible = false
    }
}

private data class WeLoadingStateProps(
    val title: String,
)