package com.qisan.wanandroid.ui.fragment

import android.os.Bundle
import androidx.paging.LoadState
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.qisan.wanandroid.R
import com.qisan.wanandroid.adapter.ArticleAdapter
import com.qisan.wanandroid.adapter.FooterAdapter
import com.qisan.wanandroid.base.BaseFragment
import com.qisan.wanandroid.databinding.FragmentWxchatChildBinding
import com.qisan.wanandroid.vm.WxChatChildViewModel
import com.qisan.wanandroid.widget.RvItemDecoration

/**
 * 公众号模块列表
 * Created by qisan 2022/6/10
 * com.qisan.wanandroid.ui.fragment
 */
class WxChatChildFragment: BaseFragment<FragmentWxchatChildBinding,WxChatChildViewModel>() {

    private var weChatId: Int = 0

    private val articleAdapter by lazy {
        ArticleAdapter()
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