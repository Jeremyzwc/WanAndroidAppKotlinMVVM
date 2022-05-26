package com.qisan.wanandroid.http

import com.qisan.wanandroid.utils.SharePreferenceUtils

/**
 * Created by qisan 2022/5/26
 * com.qisan.wanandroid.http
 */
object HttpConstant {
    const val DEFAULT_TIMEOUT: Long = 15
    const val BASE_URL = "https://www.wanandroid.com"

    const val COLLECTIONS_WEBSITE = "lg/collect"
    const val UNCOLLECTIONS_WEBSITE = "lg/uncollect"
    const val ARTICLE_WEBSITE = "article"
    const val TODO_WEBSITE = "lg/todo"
    const val COIN_WEBSITE = "lg/coin"

    const val COOKIE_NAME = "Cookie"
    const val SET_COOKIE_KEY = "set-cookie"
    const val SAVE_USER_LOGIN_KEY = "user/login"
    const val SAVE_USER_REGISTER_KEY = "user/register"


    fun encodeCookie(cookies: List<String>): String {
        val sb = StringBuilder()
        val set = HashSet<String>()
        cookies
            .map { cookie ->
                cookie.split(";".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
            }
            .forEach {
                it.filterNot { set.contains(it) }.forEach { set.add(it) }
            }
        val ite = set.iterator()
        while (ite.hasNext()) {
            val cookie = ite.next()
            sb.append(cookie).append(";")
        }
        val last = sb.lastIndexOf(";")
        if (sb.length - 1 == last) {
            sb.deleteCharAt(last)
        }
        return sb.toString()
    }

    fun saveCookie(url: String?, domain: String?, cookies: String) {
        url ?: return
        var spUrl: String by SharePreferenceUtils(url, cookies)
        @Suppress("UNUSED_VALUE")
        spUrl = cookies
        domain ?: return
        var spDomain: String by SharePreferenceUtils(domain, cookies)
        @Suppress("UNUSED_VALUE")
        spDomain = cookies
    }
}