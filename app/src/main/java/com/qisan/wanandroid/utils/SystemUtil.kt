package com.qisan.wanandroid.utils

import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.text.TextUtils
import com.qisan.wanandroid.BuildConfig
import com.qisan.wanandroid.WanApplication

/**
 * Created by qisan 2022/6/17
 * com.qisan.wanandroid.utils
 */
object SystemUtil {

    private var channel: String? = ""

    fun getChannelName(): String? {
        return if (!TextUtils.isEmpty(channel)) {
            channel
        } else {
            try {
                val appInfo: ApplicationInfo = WanApplication.context.packageManager.getApplicationInfo(WanApplication.context.packageName, PackageManager.GET_META_DATA)
                channel = appInfo.metaData.getString("MULTI_CHANNEL")
                if (TextUtils.isEmpty(channel)) {
                    channel = "google"
                }
            } catch (var1: PackageManager.NameNotFoundException) {
                var1.printStackTrace()
            }
            channel
        }
    }

    /**
     * 正式发布的环境包
     */
    fun isPublishRelease(): Boolean{
        return !BuildConfig.DEBUG && BuildConfig.IS_PUBLISH
    }


    fun isBeta(): Boolean {
        return !BuildConfig.IS_PUBLISH
    }
}