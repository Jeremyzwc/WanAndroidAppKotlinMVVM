package com.qisan.wanandroid.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.qisan.wanandroid.R
import com.qisan.wanandroid.adapter.FooterAdapter
import com.qisan.wanandroid.adapter.KnowledgeTreeAdapter
import com.qisan.wanandroid.base.BaseFragment
import com.qisan.wanandroid.databinding.FragmentKnowledgeTreeBinding
import com.qisan.wanandroid.listener.ItemClickListener
import com.qisan.wanandroid.vm.KnowledgeTreeViewModel
import com.qisan.wanandroid.widget.RvItemDecoration

/**
 * Created by qisan 2022/6/14
 * com.qisan.wanandroid.ui.fragment
 */
class KnowledgeTreeFragment: BaseFragment<FragmentKnowledgeTreeBinding, KnowledgeTreeViewModel>() {

    companion object{
        fun newInstance(): KnowledgeTreeFragment{
            val args = Bundle()

            val fragment = KnowledgeTreeFragment()
            fragment.arguments = args
            return fragment
        }
    }

    private val knowledgeTreeAdapter by lazy {
        KnowledgeTreeAdapter()
    }

    private val linearLayoutManager: LinearLayoutManager by lazy {
        LinearLayoutManager(activity)
    }


    override fun getLayoutId(): Int {
        return R.layout.fragment_knowledge_tree
    }

    override fun initData() {

        viewBinding?.recyclerView?.run {
            layoutManager = linearLayoutManager
            adapter = knowledgeTreeAdapter
            itemAnimator = DefaultItemAnimator()
            addItemDecoration(RvItemDecoration(context))
        }

        viewModel.getKnowledgeTreeList()

    }


    override fun initListener() {
        super.initListener()

        viewModel.bannerLiveData.observe(this){
            knowledgeTreeAdapter.mDatas = it
            viewBinding?.refreshLayout?.isRefreshing = false
        }

        //列表刷新
        viewBinding?.refreshLayout?.setOnRefreshListener {
            viewModel.getKnowledgeTreeList()
        }

        knowledgeTreeAdapter.setItemClick(object : ItemClickListener{
            override fun onItemClicked(v: View?, position: Int) {

            }
        })
    }

    override fun scrollToTop() {
        super.scrollToTop()

        viewBinding?.recyclerView?.run {
            if (linearLayoutManager.findFirstVisibleItemPosition() > 20) {
                scrollToPosition(0)
            } else {
                smoothScrollToPosition(0)
            }
        }
    }
}