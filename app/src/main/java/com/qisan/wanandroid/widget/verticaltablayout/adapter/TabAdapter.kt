package com.qisan.wanandroid.widget.verticaltablayout.adapter

import com.qisan.wanandroid.widget.verticaltablayout.widget.ITabView.*

/**
 * Created by QiSan 2022/6/17
 * package com.qisan.wanandroid.widget.verticaltablayout.adapter
 */
interface TabAdapter {

    fun getCount(): Int

    fun getBadge(position: Int): TabBadge?

    fun getIcon(position: Int): TabIcon?

    fun getTitle(position: Int): TabTitle?

    fun getBackground(position: Int): Int

}