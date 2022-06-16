package com.qisan.wanandroid.http

import android.text.TextUtils
import android.widget.Toast
import com.qisan.wanandroid.base.BaseViewModel
import com.qisan.wanandroid.entity.DialogLoadingEvent
import com.qisan.wanandroid.http.exception.ApiException
import com.qisan.wanandroid.http.exception.ExceptionHandle
import com.qisan.wanandroid.utils.ToastUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import java.util.concurrent.CancellationException

/**
 * Created by qisan 2022/5/27
 * com.qisan.wanandroid.http
 *
 * ViewModel扩展方法实现Flow请求模板
 */

suspend fun <T> BaseViewModel.launchFlow(showLayoutLoading: Boolean = true,isToastError :Boolean = true, request: suspend WanAndroidApiService.() -> BaseResponse<T>): Flow<BaseResponse<T>> {

    if (showLayoutLoading) {
        showLayoutLoading()
    }
    return flow {
        val response = request.invoke(apiService)
        if (!response.isSuccess) {
            throw ApiException(response.errorMsg ?: "", response.errorCode!!)
        }
        emit(response)
    }.flowOn(Dispatchers.IO)
        .onCompletion { throwable ->
            if(showLayoutLoading){
                hideLayoutLoading()
            }
            throwable?.let { throw catchException(this@launchFlow, throwable,isToastError) }
        }
}

suspend fun <T> BaseViewModel.postFlow(showDialogLoading: Boolean = true, isToastError :Boolean = true,loadingStr: String = "加载中...", request: suspend WanAndroidApiService.() -> BaseResponse<T>): Flow<BaseResponse<T>> {

    if (showDialogLoading) {
        showDialogLoading(DialogLoadingEvent(loadingStr, true))
    }
    return flow {
        val response = request.invoke(apiService)
        if (!response.isSuccess) {
            throw ApiException(response.errorMsg ?: "", response.errorCode!!)
        }
        emit(response)
    }.flowOn(Dispatchers.IO)
        .onCompletion { throwable ->
            if (showDialogLoading) {
                cloaseDialogLoading(DialogLoadingEvent("", false))
            }
            throwable?.let { throw catchException(this@postFlow, throwable,isToastError) }
        }
}

fun catchException(
    vm: BaseViewModel,
    e: Throwable,isToastError :Boolean
): Throwable {
    e.printStackTrace()
    if (e is CancellationException) {
        return e
    }
    val errerMsg = ExceptionHandle.handleException(e)
    if (isToastError && !TextUtils.isEmpty(errerMsg)) {
        vm.requestErrorEvent.postValue(errerMsg)
    }
    return e
}

/**
 * 简化在示例中catch的处理
 */
suspend fun <T> Flow<T>.next(block: suspend T.() -> Unit): Unit = catch { }.collect {
    block(it)
}

fun <T> Flow<T>.onError(block: Throwable.() -> Unit) = catch { cause ->
    block(cause)
}

