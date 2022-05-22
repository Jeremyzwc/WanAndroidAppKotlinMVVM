package com.qisan.wanandroid.base

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProvider
import com.qisan.wanandroid.WanApplication
import com.qisan.wanandroid.dialog.LoadingDialog
import com.qisan.wanandroid.utils.saveAs
import java.lang.reflect.ParameterizedType

/**
 * Created by qisan 2022/5/17
 * com.qisan.wanandroid.base
 */
abstract class BaseActivity<VB : ViewDataBinding,VM : BaseViewModel> : AppCompatActivity() {

    private lateinit var viewDataBinding : VB

    protected val viewModel: VM by lazy {
        val type = javaClass.genericSuperclass
        val modelClass: Class<VM> = type!!.saveAs<ParameterizedType>().actualTypeArguments[1].saveAs()
        ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(modelClass)
    }

    protected var isShowLoadingLayout = false

    protected val loadingDialog by lazy { LoadingDialog(this) }

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
        viewModel.application = application.saveAs()
        lifecycle.removeObserver(viewModel)
    }

    fun getActivityVm(): VM {

        return viewModel
    }

    override fun onDestroy() {
        super.onDestroy()
        viewDataBinding.unbind()
        lifecycle.removeObserver(viewModel)
    }

    fun showLoadingView(desc : String) {

        loadingDialog.showLoading(desc)
    }

    fun showLoadingView() {
        loadingDialog.showLoading()
    }
}