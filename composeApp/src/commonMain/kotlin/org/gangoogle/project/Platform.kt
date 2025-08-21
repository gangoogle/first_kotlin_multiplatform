package org.gangoogle.project

import app.cash.sqldelight.db.SqlDriver

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform


expect fun createSqlDriver():SqlDriver

