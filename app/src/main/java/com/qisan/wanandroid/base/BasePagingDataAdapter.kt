package com.qisan.wanandroid.base

import android.view.ViewGroup
import androidx.lifecycle.Lifecycle
import androidx.paging.PagingData
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

/**
 * Created by QiSan 2022/5/29
 * package com.qisan.wanandroid.base
 */
abstract class BasePagingDataAdapter<T : Any,VB: ViewBinding> : PagingDataAdapter<T, CommonRvHolder<VB>> {

    constructor() : super(itemCallback())

    constructor(diffCallback: DiffUtil.ItemCallback<T>) : super(diffCallback)

    companion object {
        fun <T : Any> itemCallback(
            areItemsTheSame: (T, T) -> Boolean = { oldItem, newItem -> oldItem == newItem },
            areContentsTheSame: (T, T) -> Boolean = { oldItem, newItem -> oldItem == newItem }
        ): DiffUtil.ItemCallback<T> {
            return object : DiffUtil.ItemCallback<T>() {
                override fun areItemsTheSame(oldItem: T, newItem: T): Boolean {
                    return areItemsTheSame(oldItem, newItem)
                }

                override fun areContentsTheSame(oldItem: T, newItem: T): Boolean {
                    return areContentsTheSame(oldItem, newItem)
                }
            }
        }
    }

    override fun onBindViewHolder(holder: CommonRvHolder<VB>, position: Int) {

        getItem(position)?.let { onBindViewHolder(holder, holder.bindingAdapterPosition, holder.binding, it) }

    }

    abstract override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommonRvHolder<VB>

    abstract fun onBindViewHolder(holder: CommonRvHolder<VB>, position: Int, binding: VB, item: T)


    open fun setPagingData(lifecycle: Lifecycle, pagingData: PagingData<T>) {
        submitData(lifecycle, pagingData)
    }


    init {

        addLoadStateListener {

        }
    }
}

open class CommonRvHolder<VB : ViewBinding>(val binding: VB) : RecyclerView.ViewHolder(binding.root) {

}