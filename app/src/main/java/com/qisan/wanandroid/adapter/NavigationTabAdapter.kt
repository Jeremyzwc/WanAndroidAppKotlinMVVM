package com.qisan.wanandroid.adapter

import android.content.Context
import androidx.core.content.ContextCompat
import com.qisan.wanandroid.R
import com.qisan.wanandroid.entity.NavigationBean
import com.qisan.wanandroid.widget.verticaltablayout.adapter.TabAdapter
import com.qisan.wanandroid.widget.verticaltablayout.widget.ITabView

/**
 * Created by chenxz on 2018/5/13.
 */
class NavigationTabAdapter(context: Context?, list: MutableList<NavigationBean>?) : TabAdapter {

    private var context: Context = context!!
    private var list = mutableListOf<NavigationBean>()

    init {
        this.list = list as MutableList<NavigationBean>
    }

    override fun getIcon(position: Int): ITabView.TabIcon? = null


    override fun getBadge(position: Int): ITabView.TabBadge? = null

    override fun getBackground(position: Int): Int = -1

    override fun getTitle(position: Int): ITabView.TabTitle? {
        return ITabView.TabTitle.Builder()
            .setContent(list[position].name)
            ?.setTextColor(
                ContextCompat.getColor(context, R.color.colorPrimary),
                ContextCompat.getColor(context, R.color.Grey500)
            )
            ?.build()
    }

    override fun getCount(): Int = list.size

}