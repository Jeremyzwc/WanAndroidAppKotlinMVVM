package com.qisan.wanandroid.adapter

import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.qisan.wanandroid.base.ViewBindingHolder

/**
 * Created by qisan 2022/5/31
 * com.qisan.wanandroid.adapter
 */
 abstract class BaseLoadStateAdapter<VB: ViewBinding>: LoadStateAdapter<ViewBindingHolder<VB>>() {


}

open class ViewBindingHolder<VB : ViewBinding>(val binding: VB) : RecyclerView.ViewHolder(binding.root) {

}