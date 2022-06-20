package com.qisan.baselib.adapter

import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

/**
 * Created by qisan 2022/6/20
 * com.qisan.baselib.adapter
 */
open class ViewBindingHolder<VB : ViewBinding>(val binding: VB) : RecyclerView.ViewHolder(binding.root) {

}