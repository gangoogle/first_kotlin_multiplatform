package org.gangoogle.project.data.db

import org.gangoogle.database.AppDatabase
import org.gangoogle.project.createSqlDriver

object DatabaseHelper {
    private val driver = createSqlDriver()
    //后续就可以直接用这个数据库进行处理了，这里的AppDatabase类就是前面自动创建的
    val database = AppDatabase(driver)
}

