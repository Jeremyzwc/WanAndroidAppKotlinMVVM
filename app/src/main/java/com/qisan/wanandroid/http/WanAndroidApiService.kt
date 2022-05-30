package com.qisan.wanandroid.http

import com.qisan.wanandroid.entity.Article
import com.qisan.wanandroid.entity.ArticleResponseBody
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * Created by qisan 2022/5/26
 * com.qisan.wanandroid.http
 */
interface WanAndroidApiService {

    /**
     * 首页文章列表
     */
    @GET("article/list/{pageNum}/json")
    suspend fun getArticles(@Path("pageNum") pageNum: Int): BaseResponse<ArticleResponseBody>

    /**
     * 首页置顶文章
     */
    @GET("article/top/json")
    suspend fun getTopArticles(): BaseResponse<MutableList<Article>>

}