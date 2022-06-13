package com.qisan.wanandroid.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.qisan.wanandroid.datasource.SquareListPagingSource
import com.qisan.wanandroid.datasource.WxChatPagingSource
import com.qisan.wanandroid.entity.Article

/**
 * Created by qisan 2022/6/10
 * com.qisan.wanandroid.vm
 */
class WxChatChildViewModel: CommonViewModel() {

    fun getWxArticleList(): LiveData<PagingData<Article>> {
        val pagingData = Pager(PagingConfig(20)) {WxChatPagingSource(this) }
            .flow
            .cachedIn(viewModelScope)

        return pagingData.asLiveData(viewModelScope.coroutineContext)
    }

}