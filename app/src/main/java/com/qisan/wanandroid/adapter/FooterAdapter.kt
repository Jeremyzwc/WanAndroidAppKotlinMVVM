package com.qisan.wanandroid.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.qisan.wanandroid.R
import com.qisan.wanandroid.databinding.ItemFooterBinding
import com.qisan.wanandroid.databinding.ItemHomeBannerBinding

/**
 * @author : leo
 * @date : 2020/5/12
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