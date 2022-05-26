package com.qisan.wanandroid.entity

import java.io.Serializable

/**
 * Created by QiSan 2022/5/27
 * package com.qisan.wanandroid.entity
 */
class BaseResponse<T>(
    val data: T? = null,
    val errorCode: Int? = null,
    val errorMsg: String? = null,
    val error: Throwable? = null
): Serializable {
    val isSuccess: Boolean
        get() = errorCode == 0
}