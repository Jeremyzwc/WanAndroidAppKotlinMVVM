package com.qisan.mvvm.base.vm

import androidx.lifecycle.ViewModel
import com.kunminx.architecture.ui.callback.UnPeekLiveData
import com.qisan.baselib.BaseApp
import com.qisan.baselib.event.DialogLoadingEvent
import com.qisan.baselib.event.LoadingErrorEvent

/**
 * Created by qisan 2022/5/17
 * com.qisan.wanandroid.base
 */
abstract class BaseViewModel : ViewModel(), ViewModelLifecycle, IView {

    lateinit var application: BaseApp

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

