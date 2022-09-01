package com.qisan.wanandroid.vm

import androidx.lifecycle.viewModelScope
import com.qisan.baselib.BaseApp
import com.qisan.wanandroid.room.ScanRecordEnity
import com.qisan.wanandroid.room.WanDB
import kotlinx.coroutines.launch


/**
 * Created by qisan 2022/6/16
 * com.qisan.wanandroid.vm
 */
class DetailContentViewModel: CommonViewModel() {


     fun recordScanContent(scanRecordEnity: ScanRecordEnity){

        viewModelScope.launch {
            WanDB.getInstance(BaseApp.context)?.getScanRecordDao()?.insert(scanRecordEnity)
        }
    }

}