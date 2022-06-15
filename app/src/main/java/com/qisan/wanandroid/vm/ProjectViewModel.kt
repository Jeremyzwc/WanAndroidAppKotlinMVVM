package com.qisan.wanandroid.vm

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.qisan.wanandroid.entity.ProjectTreeBean
import com.qisan.wanandroid.http.launchFlow
import com.qisan.wanandroid.http.next
import kotlinx.coroutines.launch

/**
 * Created by qisan 2022/5/25
 * com.qisan.wanandroid.vm
 */
class ProjectViewModel : CommonViewModel() {

    val projectTreeLiveData = MutableLiveData<MutableList<ProjectTreeBean>?>()

    fun getProjectTreeList(){
        viewModelScope.launch {
           launchFlow {
               getProjectTree()
           }.next {
               projectTreeLiveData.postValue(data)
           }
        }
    }
}