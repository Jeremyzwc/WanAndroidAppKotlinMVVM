package com.qisan.wanandroid.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
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


    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)


    }

    @LayoutRes
    protected abstract fun getLayoutId(): Int
}