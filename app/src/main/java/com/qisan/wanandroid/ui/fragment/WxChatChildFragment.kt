package com.qisan.wanandroid.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.paging.LoadState
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.qisan.mvvm.base.fragment.BaseVMFragment
import com.qisan.wanandroid.R
import com.qisan.wanandroid.adapter.ArticleAdapter
import com.qisan.wanandroid.adapter.FooterAdapter
import com.qisan.wanandroid.databinding.FragmentWxchatChildBinding
import com.qisan.baselib.listener.ItemClickListener
import com.qisan.wanandroid.ui.activity.DetailContentActivity
import com.qisan.wanandroid.vm.WxChatChildViewModel
import com.qisan.wanandroid.widget.RvItemDecoration

/**
 * 公众号模块列表
 * Created by qisan 2022/6/10
 * com.qisan.wanandroid.ui.fragment
 */
class WxChatChildFragment: BaseVMFragment<FragmentWxchatChildBinding, WxChatChildViewModel>() {

    private var weChatId: Int = 0

    private val articleAdapter by lazy {
        ArticleAdapter(requireActivity())
    }

    private val linearLayoutManager: LinearLayoutManager by lazy {
        LinearLayoutManager(activity)
    }

    companion object {
        const val ID = "id"
        fun newInstance(id: Int): WxChatChildFragment{
            val args = Bundle()
            args.putInt(ID,id)
            val fragment = WxChatChildFragment()
            fragment.arguments = args
            return fragment
        }
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_wxchat_child
    }

    override fun initData() {
        weChatId = requireArguments().getInt(ID,0)
        viewModel.wxChatId = this.weChatId


        viewBinding?.recyclerView?.run {
            articleAdapter.setVm(viewModel)
            layoutManager = linearLayoutManager
            adapter = articleAdapter.withLoadStateHeader(FooterAdapter())
            itemAnimator = DefaultItemAnimator()
            addItemDecoration(RvItemDecoration(context))
        }

        viewModel.getWxArticleList().observe(this){
            articleAdapter.setPagingData(lifecycle, it)
        }
    }

    override fun initListener() {
        super.initListener()

        articleAdapter.addLoadStateListener {
            when (it.refresh) {
                is LoadState.NotLoading -> {
                    viewBinding?.refreshLayout?.isRefreshing = false
                }
                is LoadState.Loading -> {
                    viewBinding?.refreshLayout?.isRefreshing = !viewModel.isFirstLoad
                }
                is LoadState.Error -> {
                    viewBinding?.refreshLayout?.isRefreshing = false
                }
            }
        }

        //列表刷新
        viewBinding?.refreshLayout?.setOnRefreshListener {
            viewBinding?.recyclerView?.swapAdapter(articleAdapter, true)
            articleAdapter.refresh()
        }

        articleAdapter.setItemClick(object : ItemClickListener {
            override fun onItemClicked(v: View?, position: Int) {
                val data = articleAdapter.getData(position)
                if (data != null) {
                    DetailContentActivity.startActivity(context, data.id, data.title, data.link)
                }
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