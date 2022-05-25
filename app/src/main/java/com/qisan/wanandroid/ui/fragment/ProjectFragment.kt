package com.qisan.wanandroid.ui.fragment

import com.qisan.wanandroid.R
import com.qisan.wanandroid.base.BaseFragment
import com.qisan.wanandroid.databinding.FragmentHomeBinding
import com.qisan.wanandroid.vm.HomeViewModel
import com.qisan.wanandroid.vm.ProjectViewModel

/**
 * Created by qisan 2022/5/25
 * com.qisan.wanandroid.ui.fragment
 */
class ProjectFragment : BaseFragment<FragmentHomeBinding, ProjectViewModel>() {

    override fun getLayoutId(): Int {
        return R.layout.fragment_projcet
    }

}