package com.qisan.wanandroid.base

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
/**
 * Created by zhengwencheng 2022/5/17
 * com.qisan.wanandroid.base
 */
abstract class BaseActivity<IViewDataBinding : ViewDataBinding,IBaseViewModel : BaseViewModel> : AppCompatActivity() {

    private var VDB : IViewDataBinding? = null
    protected var VM : IBaseViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initDataBinding()

    }

    @LayoutRes
    protected abstract fun getLayoutId(): Int

    private fun initDataBinding(){
        VDB = DataBindingUtil.setContentView(this,getLayoutId())
        VDB?.lifecycleOwner = this
    }

    private fun initViewModel(){

    }
}