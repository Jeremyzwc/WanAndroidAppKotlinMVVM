package com.qisan.wanandroid.base

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.qisan.wanandroid.http.BaseResponse

/**
 * PagingSource的基类
 *
 * Created by qisan 2022/5/30
 * com.qisan.wanandroid.base
 */
abstract class BasePagingSource<T: Any>: PagingSource<Int,T>() {

    override fun getRefreshKey(state: PagingState<Int, T>): Int? {
        return null
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, T> {
        return try {
            //下一页，进来为空设置默认0 如果项目的接口从1开始就设置为1
            val page = params.key ?: 0
            //如果接口可以根据自定义返回多少个数据则传
            val pageSize = params.loadSize

            val items = getPageData(page)

            return LoadResult.Page(
                data = items,
                prevKey = if (page == 0) null else page - 1,
                nextKey = if (items.isNotEmpty()) page + 1 else null
            )
        }catch (e: Exception){
            LoadResult.Error(e)
        }
    }

    abstract suspend fun getPageData(page: Int): MutableList<T>

}