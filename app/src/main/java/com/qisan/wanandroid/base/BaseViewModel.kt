package com.qisan.wanandroid.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.qisan.wanandroid.WanApplication
import com.qisan.wanandroid.utils.saveAs
import com.qisan.wanandroid.utils.saveAsUnChecked
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

/**
 * Created by qisan 2022/5/17
 * com.qisan.wanandroid.base
 */
abstract class BaseViewModel : ViewModel(), ViewModelLifecycle,IView {

    lateinit var application: WanApplication

    private val loadingViewMutableStateFlow = MutableStateFlow(false)
    //设置成只读flow来订阅接收数据
    val loadingViewStateFlow = loadingViewMutableStateFlow.asStateFlow()

    override fun showDialogLoading() {

    }

    override fun closeDialogLoading() {

    }

    override fun showLayoutLoading() {

    }

    override fun hideLayoutLoading() {

    }

    override fun showLoadError(errorMsg: String) {

    }
}

