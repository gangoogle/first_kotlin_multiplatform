package org.gangoogle.project

import android.os.Build
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import org.gangoogle.database.AppDatabase

class AndroidPlatform : Platform {
    override val name: String = "Android ${Build.VERSION.SDK_INT}"
}

actual fun getPlatform(): Platform = AndroidPlatform()


actual fun createSqlDriver(): SqlDriver {
    return AndroidSqliteDriver(AppDatabase.Schema, context = MyApp.instance , "AppDatabase.db")
}

