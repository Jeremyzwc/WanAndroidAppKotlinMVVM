package com.qisan.wanandroid.vm

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.qisan.wanandroid.entity.NavigationBean
import com.qisan.wanandroid.http.launchFlow
import com.qisan.wanandroid.http.next
import kotlinx.coroutines.launch

/**
 * Created by qisan 2022/6/14
 * com.qisan.wanandroid.vm
 */
class NavigationViewModel: CommonViewModel() {

    val navigationLiveData = MutableLiveData<MutableList<NavigationBean>?>()

    fun getNavigationData(){

        viewModelScope.launch {
            launchFlow {
                getNavigationList()
            }.next {
                navigationLiveData.postValue(data)
            }
        }
    }
}