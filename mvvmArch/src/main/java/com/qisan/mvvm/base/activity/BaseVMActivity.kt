package com.qisan.mvvm.base.activity

import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding
import com.qisan.baselib.ext.saveAs
import com.qisan.baselib.utils.ToastUtils
import com.qisan.mvvm.R
import com.qisan.mvvm.base.vm.BaseViewModel
import java.lang.reflect.ParameterizedType

/**
 * Created by qisan 2022/5/17
 * com.qisan.wanandroid.base
 */
abstract class BaseVMActivity<VB : ViewBinding, VM : BaseViewModel> : BaseActivity<VB>() {

    protected val viewModel: VM by lazy {
        val type = javaClass.genericSuperclass
        val vmClass: Class<VM> = type!!.saveAs<ParameterizedType>().actualTypeArguments[1].saveAs()
        ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(vmClass)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initViewModel()
        initCommObserver()
    }

    private fun initViewModel() {
        viewModel.application = application.saveAs()
        lifecycle.addObserver(viewModel)
    }

    fun getActivityVm(): VM {

        return viewModel
    }

    override fun onDestroy() {
        super.onDestroy()
        lifecycle.removeObserver(viewModel)
    }

    protected fun initCommObserver() {

        viewModel.dialogLoadingEvent.observe(this) {
            if (it.loadingState) {
                if (TextUtils.isEmpty(it.loadingMsg)) showDialogloading() else showDialogloading(it.loadingMsg)
            } else {
                loadingDialog.dismiss()
            }
        }

        viewModel.layoutLoadingEvent.observe(this) {
            val rlLoading = viewBinding?.root?.findViewById<RelativeLayout>(R.id.rl_loading)
            rlLoading?.visibility = if(it) View.VISIBLE else View.GONE
        }

        viewModel.loadErrorEvent.observe(this) {
            isShowErrorLayout = it.loadingErrorState
            errorMsg = it.loadingErrorMsg
            val llError = viewBinding?.root?.findViewById<LinearLayout>(R.id.ll_error)
            val tvError = viewBinding?.root?.findViewById<TextView>(R.id.tv_error)
            llError?.visibility = if(it.loadingErrorState) View.VISIBLE else View.GONE
            tvError?.text = it.loadingErrorMsg
        }

        viewModel.requestErrorEvent.observe(this) {
            ToastUtils.show(it)
        }
    }
}