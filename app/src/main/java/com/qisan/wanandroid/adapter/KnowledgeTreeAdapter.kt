package com.qisan.wanandroid.adapter

import android.text.Html
import android.view.LayoutInflater
import android.view.ViewGroup
import com.qisan.baselib.adapter.BaseRvAdapter
import com.qisan.wanandroid.databinding.ItemKnowledgeTreeListBinding
import com.qisan.wanandroid.entity.KnowledgeTreeBody
import com.qisan.baselib.adapter.ViewBindingHolder

/**
 * Created by qisan 2022/6/14
 * com.qisan.wanandroid.adapter
 */
class KnowledgeTreeAdapter: BaseRvAdapter<KnowledgeTreeBody, ItemKnowledgeTreeListBinding>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewBindingHolder<ItemKnowledgeTreeListBinding> {
        val inflate = ItemKnowledgeTreeListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewBindingHolder(inflate)
    }

    override fun bindRvData(holder: ViewBindingHolder<ItemKnowledgeTreeListBinding>, bindingAdapterPosition: Int, item: KnowledgeTreeBody) {
        holder.binding.titleFirst.text = item.name
        item.children.let {
            holder.binding.titleSecond.text = it.joinToString("    ", transform = { child ->
                Html.fromHtml(child.name)
            })
        }
    }
}