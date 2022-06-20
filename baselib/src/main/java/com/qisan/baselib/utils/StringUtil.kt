package com.qisan.baselib.utils

import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader

/**
 *Created by qisan 2022/6/16
 * com.qisan.wanandroid.utils
 */
object StringUtil {

    fun getString(stream: InputStream): String {
        val reader = BufferedReader(InputStreamReader(stream, "utf-8"))
        val sb = StringBuilder()
        var s: String? = reader.readLine()
        while (s != null) {
            sb.append(s).append("\n")
            s = reader.readLine()
        }
        return sb.toString()
    }

}