package com.qisan.wanandroid.base

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.qisan.wanandroid.WanApplication

import com.qisan.wanandroid.utils.saveAs
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

/**
 * Created by qisan 2022/5/17
 * com.qisan.wanandroid.base
 */
abstract class BaseViewModel : ViewModel(), ViewModelLifecycle,BaseViewOperate {

    lateinit var application: WanApplication

    private val loadingViewMutableStateFlow = MutableStateFlow(false)
    //设置成只读flow来订阅接收数据
    val loadingViewStateFlow = loadingViewMutableStateFlow.asStateFlow()

    override fun showLoading() {

        loadingViewMutableStateFlow.value = true
    }

    override fun closeLoading() {

        loadingViewMutableStateFlow.value = false
    }


    companion object {
        fun <T : BaseViewModel> createViewModelFactory(viewModel: T) : ViewModelProvider.Factory{
            return ViewModelFactory(viewModel)
        }
    }
}

class ViewModelFactory(private val viewModel: BaseViewModel) : ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return viewModel.saveAs()
    }
}