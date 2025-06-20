package org.example.project.view

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp
import org.example.project.ext.color


/**
 * 旋转进度条
 */
@Composable
fun ProgressBarCircle(color: Color = Color.White, bgColor: Color = "#0000ff".color) {

    val infiniteTransition = rememberInfiniteTransition()
    val animatedFloat by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1000, easing = LinearEasing)
        )
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Canvas(
            modifier = Modifier
                .fillMaxSize()
        ) {
            val canvasSize = size.width
            val radius = canvasSize / 4
            val arcSize = Size(radius * 2, radius * 2)

            val strokeWidth = 3.dp.toPx()
            drawCircle(
                color = bgColor,
                radius = size.width / 2,
            )
            drawArc(
                color = color,
                startAngle = animatedFloat,
                sweepAngle = 270f,
                useCenter = false,
                topLeft = Offset(
                    canvasSize - (radius * 3),
                    canvasSize - (radius * 3)
                ),
                size = arcSize,
                style = Stroke(width = strokeWidth, cap = StrokeCap.Round)
            )
        }
    }
}


@Composable
fun ProgressBarCircleNotFull() {
    // 创建一个无限动画
    val infiniteTransition = rememberInfiniteTransition()
    val rotation by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = 1000, // 旋转一圈的时间
                easing = LinearEasing // 线性旋转
            ),
            repeatMode = RepeatMode.Restart
        )
    )

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        // 使用 Canvas 绘制一个圆环并应用旋转动画
        Canvas(modifier = Modifier.fillMaxSize().rotate(rotation)) {
            drawArc(
                color = "#3A55F4".color,
                startAngle = 0f,
                sweepAngle = 270f, // 圆环缺少 90 度
                useCenter = false,
                style = Stroke(width = 10f)
            )
        }
    }
}


