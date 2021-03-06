package com.qisan.wanandroid.http

import com.qisan.wanandroid.entity.*
import retrofit2.http.*

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

    /**
     * 项目列表tab
     */
    @GET("project/tree/json")
    suspend fun getProjectTree(): BaseResponse<MutableList<ProjectTreeBean>>

    /**
     * 项目列表数据
     */
    @GET("project/list/{page}/json")
    suspend fun getProjectList(@Path("page") page: Int, @Query("cid") cid: Int): BaseResponse<ArticleResponseBody>


    /**
     * 登录
     */
    @POST("user/login")
    @FormUrlEncoded
    suspend fun loginWanAndroid(
        @Field("username") username: String,
        @Field("password") password: String,
    ): BaseResponse<LoginInfo>

    /**
     * 获取个人信息
     */
    @GET("/lg/coin/userinfo/json")
    suspend fun getUserInfo(): BaseResponse<UserInfo>

    /**
     * 退出登录
     */
    @GET("user/logout/json")
    suspend fun logout(): BaseResponse<Any>

    /**
     * 文章收藏
     */
    @POST("lg/collect/{id}/json")
    suspend fun addCollectArticle(@Path("id") id: Int): BaseResponse<Any>

    /**
     * 文章取消收藏
     */
    @POST("lg/uncollect_originId/{id}/json")
    suspend fun cancelCollectArticle(@Path("id") id: Int): BaseResponse<Any>
}