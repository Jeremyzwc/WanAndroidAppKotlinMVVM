package com.qisan.wanandroid.base

import android.content.Context
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.qisan.wanandroid.WanApplication
import com.qisan.wanandroid.dialog.LoadingDialog
import com.qisan.wanandroid.utils.saveAs
import java.lang.reflect.ParameterizedType

/**
 * Created by qisan 2022/5/20
 * com.qisan.wanandroid.base
 */
abstract class BaseFragment<VB : ViewDataBinding, VM : BaseViewModel> : Fragment() {

    private lateinit var viewDataBinding: VB

    protected val viewModel: VM by lazy {
        val type = javaClass.genericSuperclass
        val modelClass: Class<VM> = type!!.saveAs<ParameterizedType>().actualTypeArguments[1].saveAs()
        ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(modelClass)
    }

    protected val loadingDialog by lazy { activity?.let { LoadingDialog(it) } }

    protected var isShowLoadingLayout = false
    protected var isShowErrorLayout = false
    protected lateinit var errorMsg : String

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        viewDataBinding = DataBindingUtil.inflate<VB>(inflater, getLayoutId(), container, false)
        return run {
            viewDataBinding.lifecycleOwner = this
            viewDataBinding.root
        }

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initViewModel()

        initCommObserver()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        lifecycle.addObserver(viewModel)
    }

    private fun initViewModel() {
        viewModel.application = requireActivity().application.saveAs()
    }

    @LayoutRes
    protected abstract fun getLayoutId(): Int

    fun getFragmentVm(): VM {

        return viewModel
    }

    override fun onDestroy() {
        super.onDestroy()
        viewDataBinding.unbind()
        lifecycle.removeObserver(viewModel)
    }


    fun showDialogloading(desc: String) {
        if (loadingDialog?.isShowing == true) {
            loadingDialog?.dismiss()
        }
        loadingDialog?.showLoading(desc)
    }

    fun showDialogloading() {

        if (loadingDialog?.isShowing == true) {
            loadingDialog?.dismiss()
        }
        loadingDialog?.showLoading()
    }


    protected fun initCommObserver() {

        viewModel.dialogLoadingEvent.observe(this) {
            if (it.loadingState) {
                if (TextUtils.isEmpty(it.loadingMsg)) showDialogloading() else showDialogloading(it.loadingMsg)
            }else{
                loadingDialog?.dismiss()
            }
        }

        viewModel.layoutLoadingEvent.observe(this){
            isShowLoadingLayout = it
        }

        viewModel.loadErrorEvent.observe(this){
            isShowErrorLayout = it.loadingErrorState
            errorMsg = it.loadingErrorMsg
        }
    }
}