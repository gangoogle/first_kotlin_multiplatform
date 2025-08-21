package org.gangoogle.project.net

import org.gangoogle.project.base.AppConfig
import org.gangoogle.project.data.bean.TopWords

object UrlDispatcher {
    fun getBaseUrl(): String {
        return when (AppConfig.URL_MODE) {
            0 -> return "https://www.wanandroid.com//"
            -1 -> return "https://www.wanandroid.com//"
            else -> ""
        }
    }

}

private object UrlConstant {
    const val HOT_KEY = "hotkey/json"
}


object ApiServer {


    /**
     * 获取热词
     */
    suspend fun getHotKey() = ktorGet<List<TopWords>>(UrlConstant.HOT_KEY)()


}