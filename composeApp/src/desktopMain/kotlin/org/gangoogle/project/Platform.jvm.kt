package org.gangoogle.project

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.jdbc.sqlite.JdbcSqliteDriver
import org.gangoogle.database.AppDatabase
import java.util.Properties

class JVMPlatform : Platform {
    override val name: String = "Java ${System.getProperty("java.version")}"
}

actual fun getPlatform(): Platform = JVMPlatform()



actual fun createSqlDriver(): SqlDriver {
    return JdbcSqliteDriver("jdbc:sqlite:AppDatabase.db", Properties(), AppDatabase.Schema)
}
