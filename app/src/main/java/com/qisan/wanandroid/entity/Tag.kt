package com.qisan.wanandroid.entity

import com.google.gson.annotations.SerializedName

/**
 * Created by qisan 2022/5/27
 * com.qisan.wanandroid.entity
 */
data class Tag(
    @SerializedName("name") val name: String,
    @SerializedName("url") val url: String
)
