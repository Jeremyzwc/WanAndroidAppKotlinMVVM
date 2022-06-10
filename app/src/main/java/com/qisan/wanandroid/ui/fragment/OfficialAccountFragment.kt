package com.qisan.wanandroid.ui.fragment

import com.qisan.wanandroid.R
import com.qisan.wanandroid.base.BaseFragment
import com.qisan.wanandroid.databinding.FragmentOfficalAccountBinding
import com.qisan.wanandroid.vm.OfficalAccountViewMOdel

/**
 * 公众号模块列表
 * Created by qisan 2022/6/10
 * com.qisan.wanandroid.ui.fragment
 */
class OfficialAccountFragment: BaseFragment<FragmentOfficalAccountBinding,OfficalAccountViewMOdel>() {


    override fun getLayoutId(): Int {
        return R.layout.fragment_offical_account
    }

    override fun initData() {

    }
}