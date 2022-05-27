package com.qisan.wanandroid.http.exception

/**
 * Created by qisan 2022/5/26
 * com.qisan.wanandroid.http.exception
 */
class ApiException : RuntimeException {

    private var code: Int? = null

    constructor(throwable: Throwable, code: Int) : super(throwable) {
        this.code = code
    }

    constructor(message: String) : super(Throwable(message))

    constructor(message: String,code: Int) : super(Throwable(message)){
        this.code = code
    }
}