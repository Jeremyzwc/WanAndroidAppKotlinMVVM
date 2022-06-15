package com.qisan.wanandroid.entity

import com.google.gson.annotations.SerializedName
import java.io.Serializable

/**
 * Created by QiSan 2022/6/15
 * package com.qisan.wanandroid.entity
 */
data class UserInfo(
    @SerializedName("coinCount") val coinCount: Int, // 总积分
    @SerializedName("rank") val rank: Int, // 当前排名
    @SerializedName("userId") val userId: Int,
    @SerializedName("username") val username: String
): Serializable
