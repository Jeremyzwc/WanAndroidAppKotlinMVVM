package com.qisan.wanandroid.base

import androidx.lifecycle.ViewModel
import com.kunminx.architecture.ui.callback.UnPeekLiveData
import com.qisan.wanandroid.WanApplication
import com.qisan.wanandroid.entity.DialogLoadingEvent
import com.qisan.wanandroid.entity.LoadingErrorEvent

/**
 * Created by qisan 2022/5/17
 * com.qisan.wanandroid.base
 */
abstract class BaseViewModel : ViewModel(), ViewModelLifecycle, IView {

    lateinit var application: WanApplication

    val dialogLoadingEvent = UnPeekLiveData<DialogLoadingEvent>()
    val layoutLoadingEvent = UnPeekLiveData<Boolean>()
    val loadErrorEvent = UnPeekLiveData<LoadingErrorEvent>()
    val requestErrorEvent = UnPeekLiveData<String>()


    override fun showDialogLoading(loadingEnevt: DialogLoadingEvent) {
        dialogLoadingEvent.postValue(loadingEnevt)
    }

    override fun cloaseDialogLoading(loadingEnevt: DialogLoadingEvent) {
        dialogLoadingEvent.postValue(loadingEnevt)
    }

    override fun showLayoutLoading() {
        layoutLoadingEvent.postValue(true)
    }

    override fun hideLayoutLoading() {
        layoutLoadingEvent.postValue(false)
    }

    override fun showLoadError(loadingErrorEvent: LoadingErrorEvent) {
        loadErrorEvent.postValue(loadingErrorEvent)
    }

}

