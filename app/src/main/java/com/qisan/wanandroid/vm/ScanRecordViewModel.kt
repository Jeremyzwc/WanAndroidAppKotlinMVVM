package com.qisan.wanandroid.vm

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.qisan.baselib.BaseApp
import com.qisan.wanandroid.room.ScanRecordEnity
import com.qisan.wanandroid.room.WanDB
import kotlinx.coroutines.launch

class ScanRecordViewModel : CommonViewModel() {

    val scanRecordLiveData = MutableLiveData<MutableList<ScanRecordEnity>?>()


    fun getScanRecordList(){

        scanRecordLiveData.postValue(WanDB.getInstance(BaseApp.context)?.getScanRecordDao()?.queryAll())

    }

    fun deleteRecord(id: Long){
        viewModelScope.launch {
            WanDB.getInstance(BaseApp.context)?.getScanRecordDao()?.deleteById(id)
            scanRecordLiveData.postValue(WanDB.getInstance(BaseApp.context)?.getScanRecordDao()?.queryAll())
        }
    }
}