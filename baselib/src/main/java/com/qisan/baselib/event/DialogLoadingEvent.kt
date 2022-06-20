package com.qisan.baselib.event

/**
 * Created by qisan 2022/5/23
 * com.qisan.wanandroid.entity
 */
data class DialogLoadingEvent(
    var loadingMsg : String,
    var loadingState : Boolean = false
)
