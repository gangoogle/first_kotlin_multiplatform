package org.example.project.ext

import androidx.compose.runtime.MutableDoubleState
import androidx.compose.runtime.MutableFloatState
import androidx.compose.runtime.MutableIntState
import androidx.compose.runtime.MutableLongState
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.runtime.snapshots.SnapshotStateMap
import androidx.compose.runtime.toMutableStateList
import androidx.compose.runtime.toMutableStateMap
import androidx.compose.ui.graphics.Color
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.launch
import org.jetbrains.compose.ui.tooling.preview.Preview

/**
 * 配置好的预览
 */
@Preview()
annotation class MyPreview

val String.color: Color
    get() = strToColor(this)

private fun strToColor(str: String): Color {
    // 去除前缀 #
    val cleaned = str.removePrefix("#")
    val argb = when (cleaned.length) {
        6 -> "FF$cleaned" // #RRGGBB → 加上不透明 Alpha
        8 -> cleaned      // #AARRGGBB
        else -> throw IllegalArgumentException("Invalid color format: $str")
    }
    val colorLong = argb.toLong(16)
    val a = (colorLong shr 24 and 0xFF).toInt()
    val r = (colorLong shr 16 and 0xFF).toInt()
    val g = (colorLong shr 8 and 0xFF).toInt()
    val b = (colorLong and 0xFF).toInt()
    return Color(red = r, green = g, blue = b, alpha = a)
}


val Int.obs: MutableIntState
    get() = mutableIntStateOf(this)


val Long.obs: MutableLongState
    get() = mutableLongStateOf(this)


val Float.obs: MutableFloatState
    get() = mutableFloatStateOf(this)


val Double.obs: MutableDoubleState
    get() = mutableDoubleStateOf(this)


val String.obs: MutableState<String>
    get() = mutableStateOf(this)


val Boolean.obs: MutableState<Boolean>
    get() = mutableStateOf(this)


fun <T> obs(value: T): MutableState<T> {
    return mutableStateOf(value)
}


fun <T> listObs(vararg t: T): SnapshotStateList<T> {
    return mutableStateListOf<T>(elements = t)
}

fun <K, V> mapObs(): SnapshotStateMap<K, V> {
    return mutableStateMapOf()
}

fun <K, V> mapObs(vararg t: Pair<K, V>): SnapshotStateMap<K, V> {
    return mutableStateMapOf<K, V>(*t)
}

val <T> Collection<T>.obs: SnapshotStateList<T>
    get() = this.toMutableStateList()

val <K, V> Iterable<Pair<K, V>>.obs: SnapshotStateMap<K, V>
    get() = this.toMutableStateMap()


fun CoroutineScope.launchMain(block: suspend CoroutineScope.() -> Unit) {
    this.launch(Dispatchers.Main, block = block)
}

fun CoroutineScope.launchDefault(block: suspend CoroutineScope.() -> Unit) {
    this.launch(Dispatchers.Default, block = block)
}

fun CoroutineScope.launchIO(block: suspend CoroutineScope.() -> Unit) {
    this.launch(Dispatchers.IO, block = block)
}