package com.qisan.wanandroid.entity

import com.google.gson.annotations.SerializedName

/**
 * Created by qisan 2022/5/27
 * com.qisan.wanandroid.entity
 * 文章列表实体
 */
data class ArticleResponseBody(
    @SerializedName("curPage") val curPage: Int,
    @SerializedName("datas") var datas: MutableList<Article>,
    @SerializedName("offset") val offset: Int,
    @SerializedName("over") val over: Boolean,
    @SerializedName("pageCount") val pageCount: Int,
    @SerializedName("size") val size: Int,
    @SerializedName("total") val total: Int
)
