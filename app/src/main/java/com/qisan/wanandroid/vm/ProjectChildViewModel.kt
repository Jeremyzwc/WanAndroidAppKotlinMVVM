package com.qisan.wanandroid.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.qisan.wanandroid.datasource.ProjectPagingSource
import com.qisan.wanandroid.entity.Article
/**
 * Created by qisan 2022/5/25
 * com.qisan.wanandroid.vm
 */
class ProjectChildViewModel : CommonViewModel() {

    fun getProjectList(): LiveData<PagingData<Article>> {
        val pagingData = Pager(PagingConfig(20)) { ProjectPagingSource(this) }
            .flow
            .cachedIn(viewModelScope)

        return pagingData.asLiveData(viewModelScope.coroutineContext)
    }
}