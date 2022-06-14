package com.qisan.wanandroid.vm

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.qisan.wanandroid.entity.KnowledgeTreeBody
import com.qisan.wanandroid.http.launchFlow
import com.qisan.wanandroid.http.next
import kotlinx.coroutines.launch

/**
 * Created by qisan 2022/6/14
 * com.qisan.wanandroid.vm
 */
class KnowledgeTreeViewModel: CommonViewModel() {

    val bannerLiveData = MutableLiveData<MutableList<KnowledgeTreeBody>>()


    fun getKnowledgeTreeList(){
        viewModelScope.launch {

            launchFlow {
                getKnowledgeTree()
            }.next {
                data?.let { bannerLiveData.postValue(it) }
            }
        }
    }

}