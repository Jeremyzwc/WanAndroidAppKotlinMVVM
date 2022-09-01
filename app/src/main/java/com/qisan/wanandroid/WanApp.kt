package com.qisan.wanandroid

import com.qisan.baselib.BaseApp
import com.qisan.baselib.utils.DisplayManager
import com.qisan.wanandroid.room.WanDB

/**
 * Created by qisan 2022/5/18
 * com.qisan.wanandroid
 */
class WanApp : BaseApp() {

    override fun onCreate() {
        super.onCreate()

        DisplayManager.init(this)

        WanDB.getInstance(this)
    }

}