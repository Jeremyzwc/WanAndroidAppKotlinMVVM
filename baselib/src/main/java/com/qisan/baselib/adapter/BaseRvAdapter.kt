package com.qisan.baselib.adapter

import android.annotation.SuppressLint
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.qisan.baselib.listener.ItemClickListener
import com.qisan.baselib.listener.ItemLongClickListener
import com.qisan.baselib.listener.OnMultiClickListener

/**
 * RecyclerView.Adapter的简单封装，当非分页需求时使用
 *
 * Created by qisan 2022/6/2
 * com.qisan.wanandroid.adapter
 */
@SuppressLint("NotifyDataSetChanged")
abstract class BaseRvAdapter<T : Any?, VB : ViewBinding> : RecyclerView.Adapter<ViewBindingHolder<VB>>() {

    protected var itemClickListener: ItemClickListener? = null
    protected var itemLongClickListener: ItemLongClickListener? = null

    //BaseRvAdapter类不做分页用只set最新数据
    open var mDatas: MutableList<T> = mutableListOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    abstract override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewBindingHolder<VB>

    override fun onBindViewHolder(holder: ViewBindingHolder<VB>, position: Int) {

        holder.binding.root.setOnClickListener(object : OnMultiClickListener(){
            override fun onMultiClick(view: View?) {
                itemClickListener?.onItemClicked(view, holder.bindingAdapterPosition)
            }
        })

        holder.binding.root.setOnLongClickListener { v ->
            itemLongClickListener?.onItemLongClicked(v, holder.bindingAdapterPosition)
            true
        }

        bindRvData(holder, holder.bindingAdapterPosition, mDatas[position])
    }

    abstract fun bindRvData(holder: ViewBindingHolder<VB>, bindingAdapterPosition: Int, item: T)

    override fun getItemCount(): Int {
        return mDatas.size
    }

    open fun setItemClick(itemClickListener: ItemClickListener?) {
        this.itemClickListener = itemClickListener
    }

    open fun setItemLongClick(itemLongClickListener: ItemLongClickListener?) {
        this.itemLongClickListener = itemLongClickListener
    }
}
