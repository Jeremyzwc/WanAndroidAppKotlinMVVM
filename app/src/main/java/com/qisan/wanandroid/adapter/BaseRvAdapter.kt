package com.qisan.wanandroid.adapter

import android.annotation.SuppressLint
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

/**
 * Created by qisan 2022/6/2
 * com.qisan.wanandroid.adapter
 */
@SuppressLint("NotifyDataSetChanged")
abstract class BaseRvAdapter<T : Any?, VB : ViewBinding> : RecyclerView.Adapter<ViewBindingHolder<VB>>() {

    //BaseRvAdapter类不做分页用只set最新数据
    open var mDatas: MutableList<T> = mutableListOf()

        set(value) {
            field = value
            notifyDataSetChanged()
        }

    abstract override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewBindingHolder<VB>

    override fun onBindViewHolder(holder: ViewBindingHolder<VB>, position: Int) {
        bindRVData(holder, holder.bindingAdapterPosition, mDatas[position])
    }

    abstract fun bindRVData(holder: ViewBindingHolder<VB>, bindingAdapterPosition: Int, item: T)

    override fun getItemCount(): Int {
        return mDatas.size
    }
}