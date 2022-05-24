package com.qisan.wanandroid.ui.activity

import com.qisan.wanandroid.R
import com.qisan.wanandroid.base.BaseActivity
import com.qisan.wanandroid.databinding.ActivityMainBinding
import com.qisan.wanandroid.vm.MainViewModel

class MainActivity : BaseActivity<ActivityMainBinding,MainViewModel>(){

    override fun getLayoutId(): Int {
        return R.layout.activity_main
    }

    override fun initData() {

    }
}