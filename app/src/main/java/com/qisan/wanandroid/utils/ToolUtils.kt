package com.qisan.wanandroid.utils

/**
 * Created by qisan 2022/5/18
 * com.qisan.wanandroid.utils
 */

inline fun <reified T> Any.saveAsInline() : T{
    return this as T
}

@Suppress("UNCHECKED_CAST")
fun <T> Any.saveAs() : T{
    return this as T
}

inline fun <reified T> Any.isEqualType() : Boolean{
    return this is T
}