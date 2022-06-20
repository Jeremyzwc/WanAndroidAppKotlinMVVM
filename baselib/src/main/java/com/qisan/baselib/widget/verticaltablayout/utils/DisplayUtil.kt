package com.qisan.baselib.widget.verticaltablayout.utils

import android.content.Context

/**
 * Created by QiSan 2022/6/17
 * package com.qisan.wanandroid.widget.verticaltablayout.utils
 */
object DisplayUtil {

    fun dp2px(context: Context, dp: Float): Int {
        val scale = context.resources.displayMetrics.density
        return (dp * scale + 0.5f).toInt()
    }

    fun px2dp(context: Context, pxValue: Float): Int {
        val scale = context.resources.displayMetrics.density
        return (pxValue / scale + 0.5f).toInt()
    }

}