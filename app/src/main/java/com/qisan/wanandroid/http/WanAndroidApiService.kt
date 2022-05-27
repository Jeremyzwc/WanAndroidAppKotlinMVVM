package com.qisan.wanandroid.http

import com.qisan.wanandroid.entity.ArticleResponseBody
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * Created by qisan 2022/5/26
 * com.qisan.wanandroid.http
 */
interface WanAndroidApiService {

    @GET("article/list/{pageNum}/json")
    suspend fun getArticles(@Path("pageNum") pageNum: Int): BaseResponse<ArticleResponseBody>

}