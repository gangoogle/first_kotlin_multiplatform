package org.gangoogle.project.ext

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableDoubleState
import androidx.compose.runtime.MutableFloatState
import androidx.compose.runtime.MutableIntState
import androidx.compose.runtime.MutableLongState
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.cache
import androidx.compose.runtime.currentComposer
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
import kotlinx.coroutines.withContext
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

/**
 * 切换到主线程调度器
 */
suspend fun <T> withMain(block: suspend CoroutineScope.() -> T) = withContext(Dispatchers.Main, block)

/**
 * 切换到IO程调度器
 */
suspend fun <T> withIO(block: suspend CoroutineScope.() -> T) = withContext(Dispatchers.IO, block)

/**
 * 切换到默认调度器
 */
suspend fun <T> withDefault(block: suspend CoroutineScope.() -> T) = withContext(Dispatchers.Default, block)

/**
 * 切换到没有限制的调度器
 */
suspend fun <T> withUnconfined(block: suspend CoroutineScope.() -> T) = withContext(Dispatchers.Unconfined, block)






@Composable
fun <T> obsCache(t: T): MutableState<T> {
    return currentComposer.cache(false) {
        mutableStateOf(t)
    }
}


@Composable
fun <T> listObsCache(vararg t: T): SnapshotStateList<T> {
    return currentComposer.cache(false) {
        mutableStateListOf<T>(elements = t)
    }
}


@Composable
fun <T> listObsRemember(vararg t: T): SnapshotStateList<T> {
    return currentComposer.cache(false) {
        mutableStateListOf<T>(elements = t)
    }
}

@Composable
fun <K, V> mapObsCache(): SnapshotStateMap<K, V> {
    return currentComposer.cache(false) {
        mutableStateMapOf()
    }
}

@Composable
fun <K, V> mapObsCache(vararg t: Pair<K, V>): SnapshotStateMap<K, V> {
    return currentComposer.cache(false) {
        mutableStateMapOf<K, V>(*t)
    }
}

val <T> Collection<T>.obsCache: SnapshotStateList<T>
    @Composable
    get() = currentComposer.cache(false) {
        this.toMutableStateList()
    }

val <K, V> Iterable<Pair<K, V>>.obsCache: SnapshotStateMap<K, V>
    @Composable
    get() = currentComposer.cache(false) {
        this.toMutableStateMap()
    }


val Boolean.obsCache: MutableState<Boolean>
    @Composable
    get() = currentComposer.cache(false) {
        mutableStateOf(this)
    }


val Int.obsCache: MutableIntState
    @Composable
    get() = currentComposer.cache(false) {
        mutableIntStateOf(this)
    }

val Double.obsCache: MutableDoubleState
    @Composable
    get() = currentComposer.cache(false) {
        mutableDoubleStateOf(this)
    }

val String.obsCache: MutableState<String>
    @Composable
    get() = currentComposer.cache(false) {
        mutableStateOf(this)
    }


val Float.obsCache: MutableFloatState
    @Composable
    get() = currentComposer.cache(false) {
        mutableFloatStateOf(this)
    }

val Long.obsCache: MutableLongState
    @Composable
    get() = currentComposer.cache(false) {
        mutableLongStateOf(this)
    }