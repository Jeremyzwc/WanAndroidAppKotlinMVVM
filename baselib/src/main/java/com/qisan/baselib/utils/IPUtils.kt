package com.qisan.baselib.utils

import android.text.TextUtils
import java.util.regex.Pattern

/**
 * Created by qisan 2022/5/27
 * com.qisan.wanandroid.utils
 */
class IPUtils {

    companion object {

        fun getDealMsg(msg: String): String? {
            var msg = msg
            return if (TextUtils.isEmpty(msg)) {
                ""
            } else {
                val regex = "\\d+\\.\\d+\\.\\d+\\.\\d+"
                val pattern = Pattern.compile(regex)
                val matcher = pattern.matcher(msg)
                if (matcher.find()) {
                    val ip = matcher.group(0)
                    if (!TextUtils.isEmpty(ip)) {
                        msg = msg.replace(ip, "***")
                    }
                }
                val p = Pattern.compile("(?<=//|)((\\w)+\\.)+\\w+")
                val matcher2 = p.matcher(msg)
                if (matcher2.find()) {
                    val host = matcher2.group()
                    if (!TextUtils.isEmpty(host)) {
                        msg = msg.replace(host, "***")
                    }
                }
                msg
            }
        }

        fun getIP(msg: String?): String? {
            return if (TextUtils.isEmpty(msg)) {
                ""
            } else {
                val regex = "\\d+\\.\\d+\\.\\d+\\.\\d+"
                val pattern = Pattern.compile(regex)
                val matcher = pattern.matcher(msg)
                if (matcher.find()) {
                    val ip = matcher.group(0)
                    if (!TextUtils.isEmpty(ip)) {
                        return ip
                    }
                }
                ""
            }
        }

        fun getDoname(msg: String?): String? {
            return if (TextUtils.isEmpty(msg)) {
                ""
            } else {
                val regex = "(?<=//|)((\\w)+\\.)+\\w+"
                val pattern = Pattern.compile(regex)
                val matcher = pattern.matcher(msg)
                if (matcher.find()) {
                    val doname = matcher.group(0)
                    if (!TextUtils.isEmpty(doname)) {
                        return doname
                    }
                }
                ""
            }
        }
    }
}