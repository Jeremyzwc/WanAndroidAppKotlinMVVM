package com.qisan.wanandroid.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import com.qisan.baselib.BaseApp
import com.qisan.baselib.adapter.BaseRvAdapter
import com.qisan.baselib.adapter.ViewBindingHolder
import com.qisan.wanandroid.databinding.ItemScanRecordBinding
import com.qisan.wanandroid.room.ScanRecordEnity
import com.qisan.wanandroid.ui.activity.DetailContentActivity
import com.qisan.wanandroid.vm.ScanRecordViewModel

class ScanRecordAdapter(private val scanRecordViewModel: ScanRecordViewModel, var context: Context) : BaseRvAdapter<ScanRecordEnity,ItemScanRecordBinding>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewBindingHolder<ItemScanRecordBinding> {
        val inflate = ItemScanRecordBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewBindingHolder(inflate)
    }

    override fun bindRvData(holder: ViewBindingHolder<ItemScanRecordBinding>, bindingAdapterPosition: Int, item: ScanRecordEnity) {
        holder.binding.tvTitle.text = item.title
        holder.binding.ivClose.setOnClickListener {
            scanRecordViewModel.deleteRecord(item.id)
        }

        holder.binding.llItem.setOnClickListener {
            DetailContentActivity.startActivity(context, item.contentId, item.title, item.link)
        }
    }
}