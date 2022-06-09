package com.qisan.wanandroid.datasource

import com.qisan.wanandroid.base.BasePagingSource
import com.qisan.wanandroid.base.BaseViewModel
import com.qisan.wanandroid.entity.Article
import com.qisan.wanandroid.http.BaseResponse
import com.qisan.wanandroid.http.apiService
import com.qisan.wanandroid.vm.CommonViewModel
import com.qisan.wanandroid.vm.HomeViewModel

/**
 * Created by QiSan 2022/6/9
 * package com.qisan.wanandroid.entity
 */
class SquareListPagingSource(private val commonViewModel: CommonViewModel) : BasePagingSource<Article>() {

    override suspend fun getPageData(page: Int): MutableList<Article> {
        if(page == 0 && commonViewModel.isFirstLoad) commonViewModel.showLayoutLoading()
        val rspArticle = apiService.getSquareList(page)
        val items = rspArticle.data?.datas!!
        commonViewModel.isFirstLoad = false
        return items
    }

}