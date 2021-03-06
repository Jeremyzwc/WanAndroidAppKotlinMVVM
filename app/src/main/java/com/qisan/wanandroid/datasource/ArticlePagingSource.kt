package com.qisan.wanandroid.datasource

import com.qisan.baselib.paging.BasePagingSource
import com.qisan.wanandroid.entity.Article
import com.qisan.wanandroid.http.BaseResponse
import com.qisan.wanandroid.http.apiService
import com.qisan.wanandroid.vm.CommonViewModel

/**
 * Created by QiSan 2022/5/30
 * package com.qisan.wanandroid.entity
 */
class ArticlePagingSource(private val commonViewModel: CommonViewModel) : BasePagingSource<Article>() {

    override suspend fun getPageData(page: Int): MutableList<Article> {
        if(page == 0 && commonViewModel.isFirstLoad) commonViewModel.showLayoutLoading()
        val rspArticle = apiService.getArticles(page)
        val items = rspArticle.data?.datas!!
        var rspTopArticle: BaseResponse<MutableList<Article>> = BaseResponse()
        if (page == 0) {
            rspTopArticle = apiService.getTopArticles()
        }
        if (rspTopArticle.isSuccess && page == 0) rspTopArticle.data?.let {
            it.forEach { item ->
                item.top = "1"
            }
            items.addAll(0, it)
        }
        commonViewModel.isFirstLoad = false
        return items
    }


}