package com.qisan.wanandroid.http.exception

import android.util.Log
import com.google.gson.JsonParseException
import org.json.JSONException
import retrofit2.HttpException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import java.text.ParseException

/**
 * Created by qisan 2022/5/26
 * com.qisan.wanandroid.http.exception
 */
class ExceptionHandle {
    companion object {
        private const val TAG = "ExceptionHandle"
        var errorCode = ErrorStatus.UNKNOWN_ERROR
        var errorMsg = "请求失败，请稍后重试"

        fun handleException(e: Throwable): String {
            e.printStackTrace()
            if (e is SocketTimeoutException
                    || e is ConnectException
                    || e is HttpException
            ) { //均视为网络错误
                Log.e(TAG, "网络连接异常: " + e.message)
                errorMsg = "网络连接异常"
                errorCode = ErrorStatus.NETWORK_ERROR
            } else if (e is JsonParseException
                    || e is JSONException
                    || e is ParseException
            ) {   //均视为解析错误
                Log.e(TAG, "数据解析异常: " + e.message)
                errorMsg = "数据解析异常"
                errorCode = ErrorStatus.SERVER_ERROR
            } else if (e is ApiException) {//服务器返回的错误信息
                errorMsg = e.message.toString()
                errorCode = ErrorStatus.SERVER_ERROR
            } else if (e is UnknownHostException) {
                Log.e(TAG, "网络连接异常: " + e.message)
                errorMsg = "网络连接异常"
                errorCode = ErrorStatus.NETWORK_ERROR
            } else if (e is IllegalArgumentException) {
                errorMsg = "参数错误"
                errorCode = ErrorStatus.SERVER_ERROR
            } else {//未知错误
                try {
                    Log.e(TAG, "错误: " + e.message)
                } catch (e1: Exception) {
                    Log.e(TAG, "未知错误Debug调试 ")
                }
                errorMsg = "未知错误，可能抛锚了吧~"
                errorCode = ErrorStatus.UNKNOWN_ERROR
            }
            return errorMsg
        }

    }
}