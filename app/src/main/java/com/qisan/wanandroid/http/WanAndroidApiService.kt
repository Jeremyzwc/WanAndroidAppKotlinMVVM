package com.qisan.wanandroid.http

import com.qisan.wanandroid.entity.*
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * 接口
 *
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

    /**
     * 首页banner轮播图
     */
    @GET("banner/json")
    suspend fun getBanners(): BaseResponse<MutableList<Banner>>

    /**
     * 获取广场文章列表
     */
    @GET("user_article/list/{page}/json")
    suspend fun getSquareList(@Path("page") page: Int): BaseResponse<ArticleResponseBody>

    /**
     * 获取公众号列表
     */
    @GET("/wxarticle/chapters/json")
    suspend fun getWXChapters(): BaseResponse<MutableList<WXChapterBean>>

    /**
     * 公众号对应列表
     */
    @GET("/wxarticle/list/{id}/{page}/json")
    suspend fun getWxArticles(@Path("id") id: Int, @Path("page") page: Int): BaseResponse<ArticleResponseBody>

    /**
     * 知识体系列表
     */
    @GET("tree/json")
    suspend fun getKnowledgeTree(): BaseResponse<MutableList<KnowledgeTreeBody>>

    /**
     * 导航数据
     */
    @GET("navi/json")
    suspend fun getNavigationList(): BaseResponse<MutableList<NavigationBean>>
}