package com.qisan.wanandroid.base

import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding
import com.qisan.wanandroid.dialog.LoadingDialog
import com.qisan.wanandroid.utils.ToastUtils
import com.qisan.wanandroid.utils.saveAs
import com.qisan.wanandroid.utils.saveAsUnChecked
import java.lang.reflect.ParameterizedType

/**
 * Created by qisan 2022/5/17
 * com.qisan.wanandroid.base
 */
abstract class BaseActivity<VB : ViewBinding, VM : BaseViewModel> : AppCompatActivity() {

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
        val vmClass: Class<VM> = type!!.saveAs<ParameterizedType>().actualTypeArguments[1].saveAs()
        ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(vmClass)
    }

    protected val loadingDialog by lazy { LoadingDialog(this) }

    protected var isShowLoadingLayout = false
    protected var isShowErrorLayout = false
    protected lateinit var errorMsg: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(viewBinding?.root)
        initViewModel()
        initData()
        initCommObserver()
        initListener()
    }

    @LayoutRes
    protected abstract fun getLayoutId(): Int

    protected abstract fun initData()

    //事件监听处理，非必须
    protected fun initListener() {}


    private fun initViewModel() {
        viewModel.application = application.saveAs()
        lifecycle.addObserver(viewModel)
    }

    fun getActivityVm(): VM {

        return viewModel
    }

    override fun onDestroy() {
        super.onDestroy()
        viewBinding = null
        lifecycle.removeObserver(viewModel)
    }

    fun showDialogloading(desc: String) {
        if (loadingDialog.isShowing) {
            loadingDialog.dismiss()
        }
        loadingDialog.showLoading(desc)
    }

    fun showDialogloading() {

        if (loadingDialog.isShowing) {
            loadingDialog.dismiss()
        }
        loadingDialog.showLoading()
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
            isShowLoadingLayout = it
        }

        viewModel.loadErrorEvent.observe(this) {
            isShowErrorLayout = it.loadingErrorState
            errorMsg = it.loadingErrorMsg
        }

        viewModel.requestErrorEvent.observe(this) {
            ToastUtils.show(it)
        }
    }
}