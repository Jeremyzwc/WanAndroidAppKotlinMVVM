package com.qisan.wanandroid.webclient

import com.just.agentweb.WebViewClient


/**
 * Created by qisan 2022/6/16
 * com.qisan.wanandroid.webclient
 */
object WebClientFactory {

    val JUE_JIN = "https://juejin.cn/user/1820446987653816"

    fun create(url: String): WebViewClient {
        return when {
            url.startsWith(JUE_JIN) -> JueJinWebClient()
            else -> BaseWebClient()
        }
    }

}