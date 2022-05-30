package com.qisan.wanandroid.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.qisan.wanandroid.base.BaseViewModel
import com.qisan.wanandroid.datasource.ArticlePagingSource
import com.qisan.wanandroid.entity.Article

/**
 * Created by qisan 2022/5/25
 * com.qisan.wanandroid.vm
 */
class HomeViewModel : CommonViewModel() {

//    val articleLiveData = MutableLiveData<ArticleResponseBody?>()

//    val articleLiveData =
//        Pager(PagingConfig(20)) { ArticlePagingSource(this) }
//            .flow
//            .cachedIn(viewModelScope)
//            .asLiveData(viewModelScope.coroutineContext

    /**
     * 获取首页列表
     */
    fun getArticleList(): LiveData<PagingData<Article>> {

        val pagingData = Pager(PagingConfig(20)) { ArticlePagingSource(this) }
            .flow
            .cachedIn(viewModelScope)
        return pagingData.asLiveData(viewModelScope.coroutineContext)
    }

}