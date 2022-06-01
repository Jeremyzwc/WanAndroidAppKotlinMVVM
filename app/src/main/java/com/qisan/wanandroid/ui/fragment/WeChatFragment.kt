package com.qisan.wanandroid.ui.fragment

import android.util.Log
import com.qisan.wanandroid.R
import com.qisan.wanandroid.base.BaseFragment
import com.qisan.wanandroid.databinding.FragmentHomeBinding
import com.qisan.wanandroid.databinding.FragmentWechatBinding
import com.qisan.wanandroid.vm.HomeViewModel
import com.qisan.wanandroid.vm.WeChatViewModel

/**
 * Created by qisan 2022/5/25
 * com.qisan.wanandroid.ui.fragment
 */
class WeChatFragment : BaseFragment<FragmentWechatBinding,WeChatViewModel>() {

    override fun getLayoutId(): Int {
        return R.layout.fragment_wechat
    }

    override fun initData() {
        Log.e("WeChatFragment","initData")
    }

}