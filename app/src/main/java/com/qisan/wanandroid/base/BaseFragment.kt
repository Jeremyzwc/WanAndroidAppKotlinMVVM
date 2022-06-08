package com.qisan.wanandroid.base

import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding
import com.google.android.material.slider.BaseOnChangeListener
import com.qisan.wanandroid.R
import com.qisan.wanandroid.dialog.LoadingDialog
import com.qisan.wanandroid.utils.ToastUtils
import com.qisan.wanandroid.utils.saveAs
import com.qisan.wanandroid.utils.saveAsUnChecked
import java.lang.reflect.ParameterizedType

/**
 * Created by qisan 2022/5/20
 * com.qisan.wanandroid.base
 */
open abstract class BaseFragment<VB : ViewBinding, VM : BaseViewModel> : Fragment() {

    protected var viewBinding: VB? = null
        get() = _viewBinding

    private val _viewBinding: VB by lazy {
        val type = javaClass.genericSuperclass
        val vbClass: Class<VB> = type!!.saveAs<ParameterizedType>().actualTypeArguments[0].saveAs()
        val method = vbClass.getDeclaredMethod("inflate", LayoutInflater::class.java)
        method.invoke(this, layoutInflater)!!.saveAsUnChecked()
    }

    protected val viewModel: VM by lazy {
        val type = javaClass.genericSuperclass
        val modelClass: Class<VM> = type!!.saveAs<ParameterizedType>().actualTypeArguments[1].saveAs()
        ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(modelClass)
    }

    protected val loadingDialog by lazy { activity?.let { LoadingDialog(it) } }

    protected var isShowLoadingLayout = false
    protected var isShowErrorLayout = false
    protected lateinit var errorMsg : String
    /**
     * 是否初始化过布局
     */
    protected var isPrepared: Boolean = false
    /**
     * 是否加载过数据
     */
    protected var isDataInitiated: Boolean = false

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        isPrepared = true
        initMenu()
        return viewBinding?.root
    }

    open fun initMenu(){}

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onResume() {
        super.onResume()
        if(!isDataInitiated && !isHidden && isPrepared){
            initViewModel()
            initCommObserver()
            initData()
            initListener()
            lifecycle.addObserver(viewModel)
            isDataInitiated = true
        }
    }

    protected abstract fun initData()

    open fun initListener(){}

    private fun initViewModel() {
        viewModel.application = requireActivity().application.saveAs()
    }

    @LayoutRes
    protected abstract fun getLayoutId(): Int

    fun getFragmentVm(): VM {

        return viewModel
    }

    override fun onDestroyView() {
        super.onDestroyView()

        lifecycle.removeObserver(viewModel)
        viewBinding = null
        isDataInitiated = false
        isPrepared = false
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

    open fun scrollToTop(){

    }
}