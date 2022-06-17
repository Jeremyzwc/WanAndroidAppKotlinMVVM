package com.qisan.wanandroid.http

import com.qisan.wanandroid.BuildConfig
import com.qisan.wanandroid.WanApplication
import com.qisan.wanandroid.http.interceptor.HeaderInterceptor
import com.qisan.wanandroid.http.interceptor.SaveCookieInterceptor
import com.qisan.wanandroid.utils.FileUtil
import com.qisan.wanandroid.utils.SystemUtil
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.converter.gson.GsonConverterFactory
import java.io.ByteArrayInputStream
import java.io.InputStream
import java.security.KeyStore
import java.security.cert.CertificateFactory
import java.util.*
import java.util.concurrent.TimeUnit
import javax.net.ssl.SSLContext
import javax.net.ssl.SSLSocketFactory
import javax.net.ssl.TrustManagerFactory
import javax.net.ssl.X509TrustManager

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
//    initSslContext()
    val builder = OkHttpClient.Builder()
        .addInterceptor(getHttpLoggingInterceptor())
        .addInterceptor(HeaderInterceptor())
        .addInterceptor(SaveCookieInterceptor())
        .connectTimeout(HttpConstant.DEFAULT_TIMEOUT, TimeUnit.SECONDS)
        .readTimeout(HttpConstant.DEFAULT_TIMEOUT, TimeUnit.SECONDS)
        .writeTimeout(HttpConstant.DEFAULT_TIMEOUT, TimeUnit.SECONDS)
        .retryOnConnectionFailure(true) // 错误重连

    //设置证书流
//    if (sslSocketFactory != null && trustManager != null) {
//        builder.sslSocketFactory(sslSocketFactory!!, trustManager!!)
//    }
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


private var sslSocketFactory: SSLSocketFactory? = null
private var trustManager: X509TrustManager? = null

/**
 * 模拟的ssl签名添加
 */
private fun initSslContext() {
    // 取到证书的输入流
    // 获取自签名证书集合，由证书工厂管理
    var inputStream: InputStream? = null
    try {
        inputStream =   ByteArrayInputStream(FileUtil.readBinaryFileContent(WanApplication.context.assets.open("ssl_config.txt")))

        val certificateFactory = CertificateFactory.getInstance("X.509")
        val keyStore = KeyStore.getInstance(KeyStore.getDefaultType())
        keyStore.load(null, null)
        val certificates = certificateFactory.generateCertificates(inputStream)
        var index = 0
        for (certificate in certificates) {
            val certificateAlias = index++.toString()
            keyStore.setCertificateEntry(certificateAlias, certificate)
        }

        // 使用包含自签名证书的 KeyStore 构建一个 X509TrustManager
//            KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
//            keyManagerFactory.init(keyStore, null);
        val trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm())
        trustManagerFactory.init(keyStore)
        val trustManagers = trustManagerFactory.trustManagers
        check(!(trustManagers.size != 1 || trustManagers[0] !is X509TrustManager)) {
            ("Unexpected default trust managers:"
                    + Arrays.toString(trustManagers))
        }

        // 使用 X509TrustManager 初始化 SSLContext
        val sslContext = SSLContext.getInstance("TLS")
        sslContext.init(null, trustManagers, null)
        sslSocketFactory = sslContext.socketFactory

        trustManager = trustManagers[0] as X509TrustManager

    } catch (e: Exception) {
    } finally {
        if (inputStream != null) {
            SystemUtil.closeQuietly(inputStream)
        }
    }
}
