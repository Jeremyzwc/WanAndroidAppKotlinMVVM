package com.qisan.wanandroid.global

import com.google.gson.Gson
import com.qisan.baselib.utils.SharePreferenceUtils
import com.qisan.wanandroid.constant.Constant
import com.qisan.wanandroid.entity.LoginInfo


/**
 * Created by QiSan 2022/6/15
 * package com.qisan.wanandroid.global
 */
object WanUser {

    var loginInfo: LoginInfo? = null
    var loginInfoStr: String by SharePreferenceUtils(Constant.LOGIN_INFO, "")

    init {
        loginInfo = Gson().fromJson(loginInfoStr,LoginInfo::class.java)
    }

    fun isLogin(): Boolean{
        return loginInfo != null
    }

    fun getLogin(): LoginInfo?{
        return loginInfo
    }

}