package com.qisan.wanandroid.vm

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.qisan.wanandroid.base.BaseViewModel
import com.qisan.wanandroid.entity.UserInfo
import com.qisan.wanandroid.http.launchFlow
import com.qisan.wanandroid.http.next
import kotlinx.coroutines.launch
/**
 * Created by qisan 2022/5/18
 * com.qisan.wanandroid.vm
 */
class MainViewModel : BaseViewModel() {

    val userInfoLiveData = MutableLiveData<UserInfo?>()

    fun getUserInfo(){

        viewModelScope.launch {
            launchFlow(showLayoutLoading = false, isToastError = false) {
                getUserInfo()
            }.next {
                userInfoLiveData.postValue(data)
            }
        }
    }
}