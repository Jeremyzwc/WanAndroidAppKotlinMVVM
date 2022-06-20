package com.qisan.wanandroid.vm

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.qisan.mvvm.base.vm.BaseViewModel
import com.qisan.wanandroid.entity.LoginInfo
import com.qisan.wanandroid.http.next
import com.qisan.wanandroid.http.postFlow
import kotlinx.coroutines.launch

/**
 * Created by qisan 2022/6/15
 * com.qisan.wanandroid.vm
 */
class LoginViewModel : BaseViewModel() {

    val loginInfoLiveData = MutableLiveData<LoginInfo?>()

    fun login(username: String, password: String) {

        viewModelScope.launch {
            postFlow {
                loginWanAndroid(username, password)
            }.next {
                loginInfoLiveData.postValue(data)
            }
        }
    }
}