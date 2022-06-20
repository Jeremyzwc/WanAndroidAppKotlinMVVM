package com.qisan.wanandroid.vm

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.qisan.baselib.utils.SharePreferenceUtils
import com.qisan.mvvm.base.vm.BaseViewModel
import com.qisan.wanandroid.entity.UserInfo
import com.qisan.wanandroid.global.WanUser
import com.qisan.wanandroid.http.launchFlow
import com.qisan.wanandroid.http.next
import com.qisan.wanandroid.http.postFlow
import kotlinx.coroutines.launch
/**
 * Created by qisan 2022/5/18
 * com.qisan.wanandroid.vm
 */
class MainViewModel : BaseViewModel() {

    val userInfoLiveData = MutableLiveData<UserInfo?>()
    val logoutLiveData = MutableLiveData<Any?>()

    fun getUserInfo(){

        viewModelScope.launch {
            launchFlow(showLayoutLoading = false, isToastError = false) {
                getUserInfo()
            }.next {
                userInfoLiveData.postValue(data)
            }
        }
    }

    fun logout(loadingStr: String){
        viewModelScope.launch {
            postFlow(loadingStr = loadingStr){
                logout()
            }.next {
                WanUser.loginInfo = null
                WanUser.loginInfoStr = ""
                SharePreferenceUtils.clearPreference()

                logoutLiveData.postValue(data)
            }
        }
    }
}