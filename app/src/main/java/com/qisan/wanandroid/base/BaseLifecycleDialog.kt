package com.qisan.wanandroid.base

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatDialog
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import com.qisan.wanandroid.utils.isEqualType
import com.qisan.wanandroid.utils.saveAs

/**
 * Created by QiSan 2022/5/18
 * package com.qisan.wanandroid.base
 */
abstract class BaseLifecycleDialog<VDB : ViewDataBinding> : AppCompatDialog, DefaultLifecycleObserver {

    private lateinit var viewDataBinding: VDB
    protected val lifecycleOwner by lazy {
        if (context.isEqualType<LifecycleOwner>()) {
            context.saveAs<LifecycleOwner>().lifecycle
        } else null
    }

    constructor(context: Context) : super(context)

    constructor(context: Context, themeResId: Int) : super(context, themeResId)

    override fun onCreate(savedInstanceState: Bundle?) {
        super<AppCompatDialog>.onCreate(savedInstanceState)
        initContentView()
        lifecycleOwner?.addObserver(this)
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
}