package com.qisan.wanandroid.http.exception

/**
 * Created by qisan 2022/5/26
 * com.qisan.wanandroid.http.exception
 */
object ErrorStatus {
    /**
     * 响应成功
     */
    const val SUCCESS = 0

    /**
     * Token 过期
     */
    const val TOKEN_INVALID = 401

    /**
     * 未知错误
     */
    const val UNKNOWN_ERROR = 1002

    /**
     * 服务器内部错误
     */
    const val SERVER_ERROR = 1003

    /**
     * 网络连接超时
     */
    const val NETWORK_ERROR = 1004

    /**
     * API解析异常（或者第三方数据结构更改）等其他异常
     */
    const val API_ERROR = 1005
}