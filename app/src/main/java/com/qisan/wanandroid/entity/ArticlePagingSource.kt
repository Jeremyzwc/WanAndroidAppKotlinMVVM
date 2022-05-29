package com.qisan.wanandroid.entity

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.qisan.wanandroid.http.WanAndroidApiService
import com.qisan.wanandroid.http.apiService

/**
 * Created by QiSan 2022/5/30
 * package com.qisan.wanandroid.entity
 */
class ArticlePagingSource : PagingSource<Int, Article>() {
    override fun getRefreshKey(state: PagingState<Int, Article>): Int? {
        return null
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Article> {
        return try {
            val page = params.key ?: 0 // set page 1 as default
            val pageSize = params.loadSize
            val rspRepository = apiService.getArticles(page)
            val items = rspRepository.data?.datas
            val preKey = if (page > 0) page - 1 else null
            val nextKey = if (items?.isNotEmpty() == true) page + 1 else null
            LoadResult.Page(items!!, preKey, nextKey)
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}