package com.qisan.mvvm.base.dialog

import android.content.Context
import android.os.Bundle
import com.qisan.mvvm.R
import com.qisan.mvvm.databinding.DialogLayoutLoadingBinding

/**
 * Created by qisan 2022/5/18
 * com.qisan.wanandroid.dialog
 */
class LoadingDialog(context: Context) : BaseLifecycleDialog<DialogLayoutLoadingBinding>(context) {

    var desc = ""

    override fun initData(savedInstanceState: Bundle?) {
        super.initData(savedInstanceState)

        viewBinding?.tvLoading?.text = desc
    }

    override fun getLayoutId(): Int {
        return R.layout.dialog_layout_loading
    }

    fun showLoading(desc : String) {
        if(isShowing){
            return
        }
        this.desc = desc
        show()
    }

    fun showLoading() {
        if(isShowing){
            return
        }
        show()
    }
}