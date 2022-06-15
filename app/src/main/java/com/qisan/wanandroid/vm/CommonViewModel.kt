package com.qisan.wanandroid.vm

import com.qisan.wanandroid.base.BaseViewModel

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
}