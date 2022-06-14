package com.qisan.wanandroid.entity

import java.io.Serializable

/**
 * Created by QiSan 2022/6/15
 * package com.qisan.wanandroid.entity
 */
data class NavigationBean(
    val articles: MutableList<Article>,
    val cid: Int,
    val name: String
): Serializable
