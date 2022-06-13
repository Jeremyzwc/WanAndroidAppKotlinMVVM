package com.qisan.wanandroid.vm

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.qisan.wanandroid.base.BaseViewModel
import com.qisan.wanandroid.entity.Banner
import com.qisan.wanandroid.entity.WXChapterBean
import com.qisan.wanandroid.http.launchFlow
import com.qisan.wanandroid.http.next
import kotlinx.coroutines.launch

/**
 * Created by qisan 2022/5/25
 * com.qisan.wanandroid.vm
 */
class WeChatViewModel : BaseViewModel() {

    val wxChapterLiveData = MutableLiveData<MutableList<WXChapterBean>?>()

    fun getWxChapter(){
         viewModelScope.launch {
             launchFlow {
                 getWXChapters()
             }.next {
                 wxChapterLiveData.postValue(data)
             }
         }
    }
}