package org.gangoogle.project

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.native.NativeSqliteDriver
import org.gangoogle.database.AppDatabase
import platform.Foundation.NSUUID
import platform.UIKit.UIDevice


class IOSPlatform: Platform {
    override val name: String = UIDevice.currentDevice.systemName() + " " + UIDevice.currentDevice.systemVersion
}

actual fun getPlatform(): Platform = IOSPlatform()



actual fun createSqlDriver(): SqlDriver {
    return NativeSqliteDriver(AppDatabase.Schema, "AppDatabase.db")
}
