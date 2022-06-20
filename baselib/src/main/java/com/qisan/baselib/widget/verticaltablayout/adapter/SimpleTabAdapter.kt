package com.qisan.baselib.widget.verticaltablayout.adapter

import com.qisan.baselib.widget.verticaltablayout.widget.ITabView

/**
 * Created by QiSan 2022/6/17
 * package com.qisan.wanandroid.widget.verticaltablayout.adapter
 */
abstract class SimpleTabAdapter : TabAdapter {

    abstract override fun getCount(): Int

    override fun getBadge(position: Int): ITabView.TabBadge? {
        return null
    }

    override fun getIcon(position: Int): ITabView.TabIcon? {
        return null
    }

    override fun getTitle(position: Int): ITabView.TabTitle? {
        return null
    }

    override fun getBackground(position: Int): Int {
        return 0
    }

}