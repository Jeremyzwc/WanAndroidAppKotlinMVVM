package com.qisan.wanandroid.entity

import com.google.gson.annotations.SerializedName

/**
 * 公众号体系tab数据实体
 *
 * Created by qisan 2022/6/13
 * com.qisan.wanandroid.entity
 */
data class WXChapterBean(
    @SerializedName("children") val children: MutableList<String>,
    @SerializedName("courseId") val courseId: Int,
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("order") val order: Int,
    @SerializedName("parentChapterId") val parentChapterId: Int,
    @SerializedName("userControlSetTop") val userControlSetTop: Boolean,
    @SerializedName("visible") val visible: Int
)
