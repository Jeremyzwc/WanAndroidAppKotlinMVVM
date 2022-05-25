package com.qisan.wanandroid.ui.fragment

import com.qisan.wanandroid.R
import com.qisan.wanandroid.base.BaseFragment
import com.qisan.wanandroid.databinding.FragmentSquareBinding
import com.qisan.wanandroid.vm.SquareViewModel

/**
 * Created by qisan 2022/5/25
 * com.qisan.wanandroid.ui.fragment
 */
class SquareFragment : BaseFragment<FragmentSquareBinding, SquareViewModel>() {
    override fun getLayoutId(): Int {
        return R.layout.fragment_square
    }

    override fun initData() {
    }
}