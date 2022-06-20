package com.qisan.mvvm.base.fragment

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
 * Created by qisan 2022/5/20
 * com.qisan.wanandroid.base
 */
open abstract class BaseVMFragment<VB : ViewBinding, VM : BaseViewModel> : BaseFragment<VB>() {

    protected val viewModel: VM by lazy {
        val type = javaClass.genericSuperclass
        val modelClass: Class<VM> = type!!.saveAs<ParameterizedType>().actualTypeArguments[1].saveAs()
        ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(modelClass)
    }

    override fun onResume() {

        if(!isDataInitiated && isPrepared){
            initViewModel()
            lifecycle.addObserver(viewModel)
            initCommObserver()
        }

        super.onResume()

    }

    private fun initViewModel() {
        viewModel.application = requireActivity().application.saveAs()
    }

    fun getFragmentVm(): VM {

        return viewModel
    }

    override fun onDestroyView() {
        super.onDestroyView()

        lifecycle.removeObserver(viewModel)
    }

    protected fun initCommObserver() {

        viewModel.dialogLoadingEvent.observe(viewLifecycleOwner) {
            if (it.loadingState) {
                if (TextUtils.isEmpty(it.loadingMsg)) showDialogloading() else showDialogloading(it.loadingMsg)
            }else{
                loadingDialog?.dismiss()
            }
        }

        viewModel.layoutLoadingEvent.observe(viewLifecycleOwner){
            val rlLoading = viewBinding?.root?.findViewById<RelativeLayout>(R.id.rl_loading)
            rlLoading?.visibility = if(it) View.VISIBLE else View.GONE
        }

        viewModel.loadErrorEvent.observe(viewLifecycleOwner){
            isShowErrorLayout = it.loadingErrorState
            errorMsg = it.loadingErrorMsg
            val llError = viewBinding?.root?.findViewById<LinearLayout>(R.id.ll_error)
            val tvError = viewBinding?.root?.findViewById<TextView>(R.id.tv_error)
            llError?.visibility = if(it.loadingErrorState) View.VISIBLE else View.GONE
            tvError?.text = it.loadingErrorMsg
        }

        viewModel.requestErrorEvent.observe(viewLifecycleOwner){
            ToastUtils.show(it)
        }
    }
}