package com.qisan.wanandroid.vm

import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.qisan.wanandroid.base.BaseViewModel
import com.qisan.wanandroid.datasource.ArticlePagingSource

/**
 * Created by qisan 2022/5/25
 * com.qisan.wanandroid.vm
 */
class HomeViewModel : BaseViewModel() {

//    val articleLiveData = MutableLiveData<ArticleResponseBody?>()

    val articleLiveData  =
        Pager(PagingConfig(20)) { ArticlePagingSource() }
            .flow
            .cachedIn(viewModelScope)
            .asLiveData(viewModelScope.coroutineContext)

    /**
     * 获取首页列表
     */
//    fun getArticleList() {
//        viewModelScope.launch {
//            launchFlow {
//                getArticles(0)
//            }.next {
//                articleLiveData.postValue(data)
//            }
//        }
//    }

}