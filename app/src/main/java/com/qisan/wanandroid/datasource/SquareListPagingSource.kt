package com.qisan.wanandroid.datasource

import com.qisan.wanandroid.base.BasePagingSource
import com.qisan.wanandroid.entity.Article
import com.qisan.wanandroid.http.apiService
import com.qisan.wanandroid.vm.CommonViewModel

/**
 * Created by QiSan 2022/6/9
 * package com.qisan.wanandroid.entity
 */
class SquareListPagingSource(private val commonViewModel: CommonViewModel) : BasePagingSource<Article>() {

    override suspend fun getPageData(page: Int): MutableList<Article> {
        if (page == 0 && commonViewModel.isFirstLoad)  commonViewModel.showLayoutLoading()
        val rspArticle = apiService.getSquareList(page)
        commonViewModel.isFirstLoad = false
        commonViewModel.hideLayoutLoading()
        return rspArticle.data?.datas!!
    }

}