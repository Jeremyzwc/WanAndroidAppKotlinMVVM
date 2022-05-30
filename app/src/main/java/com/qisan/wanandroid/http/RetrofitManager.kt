package com.qisan.wanandroid.http

import com.qisan.wanandroid.BuildConfig
import com.qisan.wanandroid.http.interceptor.HeaderInterceptor
import com.qisan.wanandroid.http.interceptor.SaveCookieInterceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * Retrofit 管理类
 *
 * Created by qisan 2022/5/26
 * com.qisan.wanandroid.http
 */

val apiService: WanAndroidApiService by lazy {
    val retrofit = retrofit2.Retrofit.Builder()
        .client(okHttpClient)
        .baseUrl(HttpConstant.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    retrofit.create(WanAndroidApiService::class.java)
}

private val okHttpClient: OkHttpClient by lazy {
    val builder = OkHttpClient.Builder()
        .addInterceptor(getHttpLoggingInterceptor())
        .addInterceptor(HeaderInterceptor())
        .addInterceptor(SaveCookieInterceptor())
        .connectTimeout(HttpConstant.DEFAULT_TIMEOUT, TimeUnit.SECONDS)
        .readTimeout(HttpConstant.DEFAULT_TIMEOUT, TimeUnit.SECONDS)
        .writeTimeout(HttpConstant.DEFAULT_TIMEOUT, TimeUnit.SECONDS)
        .retryOnConnectionFailure(true) // 错误重连
    builder.build()
}

private fun getHttpLoggingInterceptor(): HttpLoggingInterceptor {
    val logging = HttpLoggingInterceptor()
    if (BuildConfig.DEBUG) {
        logging.level = HttpLoggingInterceptor.Level.BODY
    } else {
        logging.level = HttpLoggingInterceptor.Level.NONE
    }
    return logging
}
