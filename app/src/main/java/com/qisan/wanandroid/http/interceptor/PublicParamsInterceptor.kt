package com.qisan.wanandroid.http.interceptor

import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

/**
 * 设置公共参数
 * Created by QiSan 2022/5/26
 * package com.qisan.wanandroid.http.interceptor
 */
class PublicParamsInterceptor: Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val request: Request
        val modifiedUrl = originalRequest.url.newBuilder()
            // 公共参数，正式项目中一般每个接口都要提交版本号，机器，渠道等信息因为版本不同、渠道不同会有差异化
//            .addQueryParameter("version", "")
//            .addQueryParameter("device", "")
//            .addQueryParameter("channel", "")
            .build()
        request = originalRequest.newBuilder().url(modifiedUrl).build()
        return chain.proceed(request)
    }
}