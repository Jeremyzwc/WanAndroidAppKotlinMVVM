package com.qisan.wanandroid.entity

import com.google.gson.annotations.SerializedName

/**
 * Created by qisan 2022/5/27
 * com.qisan.wanandroid.entity
 */
data class Article(
    @SerializedName("apkLink") val apkLink: String,
    @SerializedName("audit") val audit: Int,
    @SerializedName("author") val author: String,
    @SerializedName("chapterId") val chapterId: Int,
    @SerializedName("chapterName") val chapterName: String,
    @SerializedName("collect") var collect: Boolean,
    @SerializedName("courseId") val courseId: Int,
    @SerializedName("desc") val desc: String,
    @SerializedName("envelopePic") val envelopePic: String,
    @SerializedName("fresh") val fresh: Boolean,
    @SerializedName("id") val id: Int,
    @SerializedName("link") val link: String,
    @SerializedName("niceDate") val niceDate: String,
    @SerializedName("niceShareDate") val niceShareDate: String,
    @SerializedName("origin") val origin: String,
    @SerializedName("prefix") val prefix: String,
    @SerializedName("projectLink") val projectLink: String,
    @SerializedName("publishTime") val publishTime: Long,
    @SerializedName("shareDate") val shareDate: String,
    @SerializedName("shareUser") val shareUser: String,
    @SerializedName("superChapterId") val superChapterId: Int,
    @SerializedName("superChapterName") val superChapterName: String,
    @SerializedName("tags") val tags: MutableList<Tag>,
    @SerializedName("title") val title: String,
    @SerializedName("type") val type: Int,
    @SerializedName("userId") val userId: Int,
    @SerializedName("visible") val visible: Int,
    @SerializedName("zan") val zan: Int,
    @SerializedName("top") var top: String
)
