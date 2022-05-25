package com.qisan.wanandroid.ui.fragment

import com.qisan.wanandroid.R
import com.qisan.wanandroid.base.BaseFragment
import com.qisan.wanandroid.databinding.FragmentHomeBinding
import com.qisan.wanandroid.databinding.FragmentSystemBinding
import com.qisan.wanandroid.vm.HomeViewModel

/**
 * Created by qisan 2022/5/25
 * com.qisan.wanandroid.ui.fragment
 */
class SystemFragment : BaseFragment<FragmentSystemBinding,HomeViewModel>() {

    override fun getLayoutId(): Int {
        return R.layout.fragment_system
    }

}