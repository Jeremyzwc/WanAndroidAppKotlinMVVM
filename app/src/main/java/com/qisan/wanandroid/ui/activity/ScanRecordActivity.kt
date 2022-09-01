package com.qisan.wanandroid.ui.activity

import android.view.MenuItem
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.qisan.mvvm.base.activity.BaseVMActivity
import com.qisan.wanandroid.R
import com.qisan.wanandroid.adapter.KnowledgeTreeAdapter
import com.qisan.wanandroid.adapter.ScanRecordAdapter
import com.qisan.wanandroid.databinding.ActivityScanRecordBinding
import com.qisan.wanandroid.vm.ScanRecordViewModel
import com.qisan.wanandroid.widget.RvItemDecoration

class ScanRecordActivity : BaseVMActivity<ActivityScanRecordBinding, ScanRecordViewModel>() {

    private val scanRecordAdapter by lazy {
        ScanRecordAdapter(viewModel,this)
    }

    private val linearLayoutManager: LinearLayoutManager by lazy {
        LinearLayoutManager(this)
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_scan_record
    }

    override fun initData() {

        viewBinding?.toolbarLayout?.toolbar?.run {
            title = "浏览记录"
            setSupportActionBar(this)
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }

        viewBinding?.recyclerView?.run {
            layoutManager = linearLayoutManager
            adapter = scanRecordAdapter
            itemAnimator = DefaultItemAnimator()
            addItemDecoration(RvItemDecoration(context))
        }

        viewModel.getScanRecordList()
    }


    override fun initListener() {
        super.initListener()

        viewModel.scanRecordLiveData.observe(this) {
            if (it != null) {
                scanRecordAdapter.mDatas = it
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }
}