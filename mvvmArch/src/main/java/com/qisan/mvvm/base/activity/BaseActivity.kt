package com.qisan.mvvm.base.activity

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding
import com.qisan.baselib.ext.saveAs
import com.qisan.baselib.ext.saveAsUnChecked
import com.qisan.baselib.utils.SettingUtil
import com.qisan.baselib.utils.StatusBarUtil
import com.qisan.mvvm.base.dialog.LoadingDialog
import java.lang.reflect.ParameterizedType

/**
 * Created by qisan 2022/6/20
 * com.qisan.wanandroid.base
 */
abstract class BaseActivity<VB : ViewBinding> : AppCompatActivity(){

    protected var viewBinding: VB? = null
        get() = _viewBinding

    private val _viewBinding: VB by lazy {
        val type = javaClass.genericSuperclass
        val vbClass: Class<VB> = type!!.saveAs<ParameterizedType>().actualTypeArguments[0].saveAs()
        val method = vbClass.getDeclaredMethod("inflate", LayoutInflater::class.java)
        method.invoke(this, layoutInflater)!!.saveAsUnChecked()
    }

    protected var mThemeColor: Int = SettingUtil.getColor()

    protected val loadingDialog by lazy { LoadingDialog(this) }

    protected var isShowLoadingLayout = false
    protected var isShowErrorLayout = false
    protected lateinit var errorMsg: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(viewBinding?.root)

        initData()
        initListener()
    }

    @LayoutRes
    protected abstract fun getLayoutId(): Int

    protected abstract fun initData()

    open fun initListener() {}

    open fun initColor() {
        mThemeColor = if (!SettingUtil.getIsNightMode()) {
            SettingUtil.getColor()
        } else {
            resources.getColor(com.qisan.baselib.R.color.colorPrimary)
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

    override fun onDestroy() {
        super.onDestroy()
        viewBinding = null
    }
}