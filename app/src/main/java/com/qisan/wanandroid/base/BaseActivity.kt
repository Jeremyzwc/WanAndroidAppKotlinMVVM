package com.qisan.wanandroid.base

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding
import com.qisan.wanandroid.R
import com.qisan.wanandroid.constant.Constant
import com.qisan.wanandroid.dialog.LoadingDialog
import com.qisan.wanandroid.utils.*
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
    protected var isLogin: Boolean by SharePreferenceUtils(Constant.LOGIN_KEY, false)

    protected var mThemeColor: Int = SettingUtil.getColor()

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

    open fun initListener() {}

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

    open fun initColor() {
        mThemeColor = if (!SettingUtil.getIsNightMode()) {
            SettingUtil.getColor()
        } else {
            resources.getColor(R.color.colorPrimary)
        }
        StatusBarUtil.setColor(this, mThemeColor, 0)
        if (this.supportActionBar != null) {
            this.supportActionBar?.setBackgroundDrawable(ColorDrawable(mThemeColor))
        }

        if (SettingUtil.getNavBar()) {
            window.navigationBarColor = mThemeColor
        } else {
            window.navigationBarColor = Color.BLACK
        }
    }
}