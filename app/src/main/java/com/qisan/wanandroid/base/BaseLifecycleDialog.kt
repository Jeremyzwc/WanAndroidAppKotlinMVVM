package com.qisan.wanandroid.base

import android.content.Context
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.WindowManager
import androidx.annotation.FloatRange
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatDialog
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import com.qisan.wanandroid.utils.isEqualType
import com.qisan.wanandroid.utils.saveAs

/**
 * Created by QiSan 2022/5/18
 * package com.qisan.wanandroid.base
 */
abstract class BaseLifecycleDialog<VDB : ViewDataBinding> : AlertDialog, DefaultLifecycleObserver {

    protected lateinit var viewDataBinding: VDB

    //属性设置
    private var width: Int = ViewGroup.LayoutParams.WRAP_CONTENT
    private var height: Int = ViewGroup.LayoutParams.WRAP_CONTENT
    private var gravity: Int = Gravity.CENTER
    private var animRes: Int = -1
    private var dimAmount: Float = 0.5f
    private var alpha: Float = 1f

    protected val lifecycleOwner by lazy {
        if (context.isEqualType<LifecycleOwner>()) {
            context.saveAs<LifecycleOwner>().lifecycle
        } else null
    }

    constructor(context: Context) : super(context)

    constructor(context: Context, themeResId: Int) : super(context, themeResId)

    override fun onCreate(savedInstanceState: Bundle?) {
        super<AlertDialog>.onCreate(savedInstanceState)
        initContentView()
        lifecycleOwner?.addObserver(this)

        initData(savedInstanceState)

        refreshAttributes()
    }

    override fun dismiss() {

        lifecycleOwner?.removeObserver(this)
        super.dismiss()
    }

    override fun onDestroy(owner: LifecycleOwner) {
        super.onDestroy(owner)
        if (isShowing) {
            dismiss()
        }
    }

    protected abstract @LayoutRes
    fun getLayoutId(): Int

    private fun initContentView() {
        viewDataBinding = DataBindingUtil.inflate(
            LayoutInflater.from(context),
            getLayoutId(), null, false
        )
        setContentView(viewDataBinding.root)
    }

    protected open fun initData(savedInstanceState: Bundle?) {

    }

    /**
     * 设置宽度
     */
    fun setWidth(width: Int) {
        this.width = width
    }

    /**
     * 设置高度
     */
    fun setHeight(height: Int) {
        this.height = height
    }

    /**
     * 设置位置
     */
    fun setGravity(gravity: Int) {
        this.gravity = gravity
    }

    /**
     * 设置窗口透明度
     */
    fun setDimAmount(@FloatRange(from = 0.0, to = 1.0) dimAmount: Float) {
        this.dimAmount = dimAmount
    }

    /**
     * 设置背景透明度
     */
    fun setAlpha(@FloatRange(from = 0.0, to = 1.0) alpha: Float) {
        this.alpha = alpha
    }


    /**
     * 设置显示和隐藏动画
     */
    fun setAnimationRes(animation: Int) {
        this.animRes = animation
    }

    /**
     * 刷新
     */
    fun refreshAttributes() {
        window?.let {
            val params: WindowManager.LayoutParams = it.attributes
            params.width = width
            params.height = height
            params.gravity = gravity
            params.windowAnimations = animRes
            params.dimAmount = dimAmount
            params.alpha = alpha
            params.windowAnimations = animRes
            it.attributes = params
        }
    }


}