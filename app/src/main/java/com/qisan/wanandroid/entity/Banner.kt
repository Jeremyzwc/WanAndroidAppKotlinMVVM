package com.qisan.wanandroid.entity

import com.google.gson.annotations.SerializedName

/**
 * Created by QiSan 2022/5/31
 * package com.qisan.wanandroid.entity
 */
data class Banner(
    @SerializedName("desc") val desc: String,
    @SerializedName("id") val id: Int,
    @SerializedName("imagePath") val imagePath: String,
    @SerializedName("isVisible") val isVisible: Int,
    @SerializedName("order") val order: Int,
    @SerializedName("title") val title: String,
    @SerializedName("type") val type: Int,
    @SerializedName("url") val url: String
)