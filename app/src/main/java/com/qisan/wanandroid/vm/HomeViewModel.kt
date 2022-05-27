package com.qisan.wanandroid.vm

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.qisan.wanandroid.base.BaseViewModel
import com.qisan.wanandroid.entity.ArticleResponseBody
import com.qisan.wanandroid.http.launchFlow
import com.qisan.wanandroid.http.next
import kotlinx.coroutines.launch

/**
 * Created by qisan 2022/5/25
 * com.qisan.wanandroid.vm
 */
class HomeViewModel : BaseViewModel() {

    val articleLiveData = MutableLiveData<ArticleResponseBody?>()

    /**
     * 获取首页列表
     */
    fun getArticleList() {
        viewModelScope.launch {
            launchFlow {
                getArticles(0)
            }.next {
                articleLiveData.postValue(data)
            }
        }
    }

}