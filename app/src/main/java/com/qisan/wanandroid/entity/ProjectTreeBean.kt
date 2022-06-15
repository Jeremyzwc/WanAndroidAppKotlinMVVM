package com.qisan.wanandroid.entity

import com.google.gson.annotations.SerializedName
import java.io.Serializable

/**
 * Created by qisan 2022/6/15
 * com.qisan.wanandroid.entity
 */
data class ProjectTreeBean(
    @SerializedName("children") val children: List<Any>,
    @SerializedName("courseId") val courseId: Int,
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("order") val order: Int,
    @SerializedName("parentChapterId") val parentChapterId: Int,
    @SerializedName("visible") val visible: Int
): Serializable
