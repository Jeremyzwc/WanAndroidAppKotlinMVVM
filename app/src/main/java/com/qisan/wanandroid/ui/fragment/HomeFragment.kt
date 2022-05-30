package com.qisan.wanandroid.ui.fragment

import android.view.View
import androidx.paging.LoadState
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.qisan.wanandroid.R
import com.qisan.wanandroid.adapter.ArticleAdapter
import com.qisan.wanandroid.base.BaseFragment
import com.qisan.wanandroid.databinding.FragmentHomeBinding
import com.qisan.wanandroid.vm.HomeViewModel
import com.qisan.wanandroid.widget.RvItemDecoration

/**
 * Created by qisan 2022/5/25
 * com.qisan.wanandroid.ui.fragment
 */
class HomeFragment : BaseFragment<FragmentHomeBinding,HomeViewModel>() {

    private var bannerView: View? = null

    private val mAdapter by lazy {
        ArticleAdapter()
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_home
    }

    override fun initData() {
        viewBinding?.recyclerView?.run {
            layoutManager = LinearLayoutManager(activity)
            adapter = mAdapter
            itemAnimator = DefaultItemAnimator()
            addItemDecoration(RvItemDecoration(context))
        }

        viewModel.getArticleList().observe(this){
            mAdapter.setPagingData(lifecycle,it)
        }
    }


    override fun initListener() {
        super.initListener()

        mAdapter.addLoadStateListener {
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

        viewBinding?.refreshLayout?.setOnRefreshListener {
            viewBinding?.recyclerView?.swapAdapter(mAdapter,true)
            mAdapter.refresh()
        }
    }
}