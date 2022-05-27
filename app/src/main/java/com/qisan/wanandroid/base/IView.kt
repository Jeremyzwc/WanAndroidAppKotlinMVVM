package com.qisan.wanandroid.base

import com.qisan.wanandroid.entity.DialogLoadingEvent
import com.qisan.wanandroid.entity.LoadingErrorEvent

/**
 * Created by qisan 2022/5/20
 * com.qisan.wanandroid.base
 */
interface IView {

    /**
     * 展示dialogloading,如用在登录接口提交时
     */
    fun showDialogLoading(loadingEnevt: DialogLoadingEvent)

    fun cloaseDialogLoading(loadingEnevt: DialogLoadingEvent)
    /**
     * 页面加载数据时的loading提示
     */
    fun showLayoutLoading()

    /**
     * 隐藏布局加载
     */
    fun hideLayoutLoading()

    /***
     * 数据加载失败 或者无数据处理
     */
    fun showLoadError(loadingErrorEvent: LoadingErrorEvent)

    /**
     * showTosat
     */

}