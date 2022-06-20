package com.qisan.baselib.adapter

import androidx.annotation.NonNull
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

/**
 * Created by qisan 2022/6/13
 * com.qisan.wanandroid.adapter
 */
class CommonPageAdapter(@NonNull fm: FragmentManager, behavior: Int, private var fragments: List<Fragment>, private var tabList: List<String>) : FragmentPagerAdapter(fm, behavior) {

    override fun getCount(): Int {
        return fragments.size
    }

    override fun getItem(position: Int): Fragment {
        return fragments[position]
    }

    override fun getPageTitle(position: Int): CharSequence {
        return tabList[position]
    }
}