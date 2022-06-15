package com.qisan.wanandroid.entity

import com.google.gson.annotations.SerializedName
import java.io.Serializable

/**
 * Created by qisan 2022/6/15
 * com.qisan.wanandroid.entity
 */
data class LoginInfo(
    @SerializedName("chapterTops") val chapterTops: MutableList<String>,
    @SerializedName("collectIds") val collectIds: MutableList<String>,
    @SerializedName("email") val email: String,
    @SerializedName("icon") val icon: String,
    @SerializedName("id") val id: Int,
    @SerializedName("password") val password: String,
    @SerializedName("token") val token: String,
    @SerializedName("type") val type: Int,
    @SerializedName("username") val username: String
): Serializable
