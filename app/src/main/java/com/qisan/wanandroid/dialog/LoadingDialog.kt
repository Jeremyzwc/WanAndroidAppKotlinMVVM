package com.qisan.wanandroid.dialog

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import com.qisan.wanandroid.R
import com.qisan.wanandroid.base.BaseLifecycleDialog
import com.qisan.wanandroid.databinding.LayoutLoadingDialogBinding

/**
 * Created by qisan 2022/5/18
 * com.qisan.wanandroid.dialog
 */
class LoadingDialog(context: Context) : BaseLifecycleDialog<LayoutLoadingDialogBinding>(context) {

    var desc = ""

    override fun initData(savedInstanceState: Bundle?) {
        super.initData(savedInstanceState)

        viewDataBinding.desc = desc
    }

    override fun getLayoutId(): Int {
        return R.layout.layout_loading_dialog
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