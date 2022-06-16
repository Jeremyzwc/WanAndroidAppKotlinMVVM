package com.qisan.wanandroid.vm

import androidx.lifecycle.viewModelScope
import com.qisan.wanandroid.base.BaseViewModel
import com.qisan.wanandroid.http.launchFlow
import kotlinx.coroutines.launch

/**
 * Created by QiSan 2022/5/30
 * package com.qisan.wanandroid.vm
 */
open class CommonViewModel: BaseViewModel() {
    var isFirstLoad = true
    //公众号id
    var wxChatId: Int = 0
    //项目列表的id
    var cid: Int = 0

//    val addCollectArticleLiveData = MutableLiveData<Any?>()
//    val cancelCollectArticleLiveData = MutableLiveData<Any?>()

    open fun addCollectArticle(id: Int){
        viewModelScope.launch {
            launchFlow(showLayoutLoading = false) {
                addCollectArticle(id)
            }
        }
    }

    open fun cancelCollectArticle(id: Int){
        viewModelScope.launch {
            launchFlow(showLayoutLoading = false) {
                cancelCollectArticle(id)
            }
        }
    }
}