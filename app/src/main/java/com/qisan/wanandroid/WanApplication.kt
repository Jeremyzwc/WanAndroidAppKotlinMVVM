package com.qisan.wanandroid

import android.app.Application
import android.content.Context
import kotlin.properties.Delegates

/**
 * Created by qisan 2022/5/18
 * com.qisan.wanandroid
 */
class WanApplication : Application() {

    companion object{
        var context: Context by Delegates.notNull()
            private set
    }

    override fun onCreate() {
        super.onCreate()

        context = applicationContext
    }

}