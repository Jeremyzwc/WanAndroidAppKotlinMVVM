package com.qisan.wanandroid.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import com.qisan.baselib.adapter.ViewBindingHolder
import com.qisan.wanandroid.databinding.ItemFooterBinding

/**
 * paging添加的页脚
 * Created by QiSan 2022/5/29
 * package com.qisan.wanandroid.base
 */
class FooterAdapter : LoadStateAdapter<ViewBindingHolder<ItemFooterBinding>>() {

    override fun onBindViewHolder(holder: ViewBindingHolder<ItemFooterBinding>, loadState: LoadState) {
        if (loadState.endOfPaginationReached) {
            holder.binding.progressBar.visibility = View.GONE
            holder.binding.tvLoading.text = "我们是有底线的"
        } else {
            holder.binding.progressBar.visibility = View.VISIBLE
            holder.binding.tvLoading.text = "加载中。。。"
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): ViewBindingHolder<ItemFooterBinding> {
        val inflate = ItemFooterBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewBindingHolder(inflate)

    }
}