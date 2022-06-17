package com.qisan.wanandroid.widget.verticaltablayout.adapter

import com.qisan.wanandroid.widget.verticaltablayout.widget.ITabView.*

/**
 * Created by QiSan 2022/6/17
 * package com.qisan.wanandroid.widget.verticaltablayout.adapter
 */
abstract class SimpleTabAdapter : TabAdapter {

    abstract override fun getCount(): Int

    override fun getBadge(position: Int): TabBadge? {
        return null
    }

    override fun getIcon(position: Int): TabIcon? {
        return null
    }

    override fun getTitle(position: Int): TabTitle? {
        return null
    }

    override fun getBackground(position: Int): Int {
        return 0
    }

}