package com.qisan.wanandroid

import com.qisan.wanandroid.base.BaseActivity
import com.qisan.wanandroid.databinding.ActivityMainBinding
import com.qisan.wanandroid.vm.MainViewModel

class MainActivity : BaseActivity<ActivityMainBinding,MainViewModel>(){

    override fun getLayoutId(): Int {
        return R.layout.activity_main
    }

    override fun bindViewModel(): MainViewModel {
        return MainViewModel()
    }

    override fun initData() {

    }
}