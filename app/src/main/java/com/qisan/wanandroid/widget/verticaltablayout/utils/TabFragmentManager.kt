package com.qisan.wanandroid.widget.verticaltablayout.utils

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.qisan.wanandroid.widget.verticaltablayout.VerticalTabLayout
import com.qisan.wanandroid.widget.verticaltablayout.widget.TabView

/**
 * Created by QiSan 2022/6/17
 * package com.qisan.wanandroid.widget.verticaltablayout.utils
 */
class TabFragmentManager {

    private var mManager: FragmentManager? = null
    private var mContainerResid = 0
    private lateinit var mFragments: List<Fragment>
    private var mTabLayout: VerticalTabLayout? = null
    private var mListener: VerticalTabLayout.OnTabSelectedListener? = null

    constructor(manager: FragmentManager?, fragments: List<Fragment>, tabLayout: VerticalTabLayout?) {
        mManager = manager
        mFragments = fragments
        mTabLayout = tabLayout
        mListener = OnFragmentTabSelectedListener()
        mTabLayout?.addOnTabSelectedListener(mListener)
    }

    constructor(manager: FragmentManager?, containerResid: Int, fragments: List<Fragment>, tabLayout: VerticalTabLayout?) {
        TabFragmentManager(manager, fragments, tabLayout)
        mContainerResid = containerResid
        changeFragment()
    }

    fun changeFragment() {
        val ft = mManager?.beginTransaction()
        val position = mTabLayout?.getSelectedTabPosition()
        val addedFragments = mManager?.fragments
        for (i in mFragments.indices) {
            val fragment = mFragments[i]
            if ((!addedFragments?.contains(fragment)!!) && mContainerResid != 0) {
                ft?.add(mContainerResid, fragment)
            }
            if (mFragments.size > position!! && i == position
                || mFragments.size <= position && i == mFragments.size - 1
            ) {
                ft?.show(fragment)
            } else {
                ft?.hide(fragment)
            }
        }
        ft?.commit()
        mManager?.executePendingTransactions()
    }

    fun detach() {
        val ft = mManager?.beginTransaction()
        for (fragment in mFragments) {
            ft?.remove(fragment)
        }
        ft?.commit()
        mManager?.executePendingTransactions()
        mManager = null
        mFragments = emptyList()
        mTabLayout?.removeOnTabSelectedListener(mListener)
        mListener = null
        mTabLayout = null
    }


    inner class OnFragmentTabSelectedListener : VerticalTabLayout.OnTabSelectedListener {

        override fun onTabSelected(tab: TabView?, position: Int) {
            changeFragment()
        }

        override fun onTabReselected(tab: TabView?, position: Int) {
            TODO("Not yet implemented")
        }
    }

}