package com.qisan.baselib

import android.app.Application
import android.content.Context
import kotlin.properties.Delegates

/**
 * Created by qisan 2022/6/20
 * com.qisan.baselib
 */
open abstract class BaseApp : Application() {

    companion object{
       var context: Context by Delegates.notNull()
    }

    override fun onCreate() {
        super.onCreate()

        context = applicationContext
    }
}