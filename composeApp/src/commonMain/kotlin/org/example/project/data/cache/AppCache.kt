package org.example.project.data.cache

import com.russhwolf.settings.Settings
import com.russhwolf.settings.get

/**
 * 设置缓存
 */
object AppCache {
    private val setting by lazy { Settings() }

    var userName: String
        get() = setting.get("userName", "")
        set(value) = setting.putString("userName", value = value)

}