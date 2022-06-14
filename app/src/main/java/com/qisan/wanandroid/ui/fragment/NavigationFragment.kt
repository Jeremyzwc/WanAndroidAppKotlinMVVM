package com.qisan.wanandroid.ui.fragment

import android.os.Bundle
import com.qisan.wanandroid.R
import com.qisan.wanandroid.base.BaseFragment
import com.qisan.wanandroid.databinding.FragmentNavigationBinding
import com.qisan.wanandroid.vm.NavigationViewModel

/**
 * Created by qisan 2022/6/14
 * com.qisan.wanandroid.ui.fragment
 */
class NavigationFragment: BaseFragment<FragmentNavigationBinding,NavigationViewModel>() {

    companion object{
        fun newInstance(): NavigationFragment{
            val args = Bundle()

            val fragment = NavigationFragment()
            fragment.arguments = args
            return fragment
        }
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_navigation
    }

    override fun initData() {

    }

}