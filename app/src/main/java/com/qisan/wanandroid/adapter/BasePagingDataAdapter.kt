package com.qisan.wanandroid.adapter

import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Lifecycle
import androidx.paging.PagingData
import androidx.paging.PagingDataAdapter
import androidx.paging.filter
import androidx.recyclerview.widget.DiffUtil
import androidx.viewbinding.ViewBinding
import com.qisan.baselib.adapter.ViewBindingHolder
import com.qisan.baselib.listener.ItemClickListener
import com.qisan.baselib.listener.ItemLongClickListener
import com.qisan.baselib.listener.OnMultiClickListener
import com.qisan.wanandroid.vm.CommonViewModel


/**
 * Created by QiSan 2022/5/29
 * package com.qisan.wanandroid.base
 */
abstract class BasePagingDataAdapter<T : Any, VB : ViewBinding> : PagingDataAdapter<T, ViewBindingHolder<VB>> {

    protected lateinit var mPagingData: PagingData<T>
    protected lateinit var mLifecycle: Lifecycle

    protected var itemClickListener: ItemClickListener? = null
    protected var itemLongClickListener: ItemLongClickListener? = null
    protected var viewModel: CommonViewModel? = null
    constructor() : super(itemCallback())

    constructor(diffCallback: DiffUtil.ItemCallback<T>) : super(diffCallback)

    companion object {
        fun <T : Any> itemCallback(
            areItemsTheSame: (T, T) -> Boolean = { oldItem, newItem -> oldItem.javaClass == newItem.javaClass },
            areContentsTheSame: (T, T) -> Boolean = { oldItem, newItem -> oldItem == newItem },
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

        getItem(position)?.let{ onBindViewHolder(holder, holder.bindingAdapterPosition, holder.binding, it) }
    }

    abstract override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewBindingHolder<VB>

    abstract fun onBindViewHolder(holder: ViewBindingHolder<VB>, position: Int, binding: VB, item: T)

    open fun setPagingData(lifecycle: Lifecycle, pagingData: PagingData<T>) {
        this.mLifecycle = lifecycle
        this.mPagingData = pagingData
        submitData(lifecycle, pagingData)
    }


    open fun setItemClick(itemClickListener: ItemClickListener?) {
        this.itemClickListener = itemClickListener
    }

    open fun setItemLongClick(itemLongClickListener: ItemLongClickListener?) {
        this.itemLongClickListener = itemLongClickListener
    }

    open fun setVm(viewModel: CommonViewModel?) {
        this.viewModel = viewModel
    }

    /**
     * 获取对应position的item元素
     */
    fun getData(position: Int): T? {
        return getItem(position)
    }

    /**
     * 过滤要移除的数据
     */
    fun filterItem(predicate: suspend (T) -> Boolean) {
        if (!this::mPagingData.isInitialized || !this::mLifecycle.isInitialized) {
            throw IllegalArgumentException("To edit data, you must use the 'setPagingData' method")
        }
        mPagingData = mPagingData.filter(predicate)
        submitData(mLifecycle, mPagingData)
    }

    /**
     * 根据元素移除数据
     * @param item 要移除的条目
     */
    fun removeItem(item: T) {
        filterItem { it != item }
    }

    /**
     * 根据位置移除数据
     * @param position
     */
    fun removeItem(position: Int) {
        filterItem { it != getData(position) }
    }
}