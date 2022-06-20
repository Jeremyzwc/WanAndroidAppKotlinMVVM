package com.qisan.baselib.widget.verticaltablayout.widget

import android.content.Context
import android.widget.Checkable
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import q.rorbin.badgeview.Badge

/**
 * Created by QiSan 2022/6/18
 * package com.qisan.wanandroid.widget.verticaltablayout.widget
 */
abstract class TabView(context: Context) : FrameLayout(context), Checkable, ITabView{

    override fun getTabView(): TabView? {
        return this
    }

    @Deprecated("")
    abstract fun getIconView(): ImageView?

    abstract fun getTitleView(): TextView?

    abstract fun getBadgeView(): Badge?
}