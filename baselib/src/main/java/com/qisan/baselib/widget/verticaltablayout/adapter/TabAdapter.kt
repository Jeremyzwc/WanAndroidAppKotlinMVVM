package com.qisan.baselib.widget.verticaltablayout.adapter

import com.qisan.baselib.widget.verticaltablayout.widget.ITabView

/**
 * Created by QiSan 2022/6/17
 * package com.qisan.wanandroid.widget.verticaltablayout.adapter
 */
interface TabAdapter {

    fun getCount(): Int

    fun getBadge(position: Int): ITabView.TabBadge?

    fun getIcon(position: Int): ITabView.TabIcon?

    fun getTitle(position: Int): ITabView.TabTitle?

    fun getBackground(position: Int): Int

}