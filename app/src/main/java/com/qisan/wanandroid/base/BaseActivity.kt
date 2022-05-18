package com.qisan.wanandroid.base

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProvider
import com.qisan.wanandroid.WanApplication
import com.qisan.wanandroid.utils.saveAs

/**
 * Created by qisan 2022/5/17
 * com.qisan.wanandroid.base
 */
abstract class BaseActivity<VB : ViewDataBinding,VM : BaseViewModel> : AppCompatActivity() {

    private lateinit var viewDataBinding : VB
    protected lateinit var viewModel : VM

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initDataBinding()
        initViewModel()

        initData()
    }

    @LayoutRes
    protected abstract fun getLayoutId(): Int

    protected abstract fun initData()

    //事件监听处理，非必须
    protected fun initListener(){}

    private fun initDataBinding(){
        viewDataBinding = DataBindingUtil.setContentView(this,getLayoutId())
        viewDataBinding.lifecycleOwner = this
    }

    private fun initViewModel(){
        //拿到实际的ViewModel
        val vm = bindViewModel()
        viewModel = ViewModelProvider(this,BaseViewModel.createViewModelFactory(vm)).get(vm::class.java)
        viewModel.application = application.saveAs()
        lifecycle.removeObserver(viewModel)
    }

    //在activity中返回对应的ViewModel
    protected abstract fun bindViewModel(): VM

    fun getActivityVm(): VM {

        return viewModel
    }

    override fun onDestroy() {
        super.onDestroy()
        viewDataBinding.unbind()
        lifecycle.removeObserver(viewModel)
    }
}