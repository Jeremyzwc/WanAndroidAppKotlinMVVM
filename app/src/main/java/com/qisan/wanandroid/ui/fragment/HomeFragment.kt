package com.qisan.wanandroid.ui.fragment

import android.annotation.SuppressLint
import android.view.View
import androidx.paging.LoadState
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.qisan.mvvm.base.fragment.BaseVMFragment
import com.qisan.wanandroid.R
import com.qisan.wanandroid.adapter.ArticleAdapter
import com.qisan.wanandroid.adapter.FooterAdapter
import com.qisan.wanandroid.adapter.HomeBannerAdapter
import com.qisan.wanandroid.databinding.FragmentHomeBinding
import com.qisan.wanandroid.entity.Banner
import com.qisan.baselib.listener.ItemClickListener
import com.qisan.wanandroid.ui.activity.DetailContentActivity
import com.qisan.wanandroid.vm.HomeViewModel
import com.qisan.wanandroid.widget.RvItemDecoration

/**
 * Created by qisan 2022/5/25
 * com.qisan.wanandroid.ui.fragment
 */
class HomeFragment : BaseVMFragment<FragmentHomeBinding, HomeViewModel>() {

    private val homeBannerAdapter by lazy {
        HomeBannerAdapter(context)
    }

    private val articleAdapter by lazy {
        ArticleAdapter(requireActivity())
    }

    private val wrapRecyclerAdapter by lazy {
        articleAdapter.setVm(viewModel)
        articleAdapter.withLoadStateFooter(FooterAdapter())
    }

    private val linearLayoutManager: LinearLayoutManager by lazy {
        LinearLayoutManager(activity)
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_home
    }

    @SuppressLint("InflateParams")
    override fun initData() {

        viewModel.getBanner()

        viewBinding?.recyclerView?.run {
            layoutManager = linearLayoutManager
            wrapRecyclerAdapter.addAdapter(0,homeBannerAdapter)
            adapter = wrapRecyclerAdapter
            itemAnimator = DefaultItemAnimator()
            addItemDecoration(RvItemDecoration(context))
        }
    }

    override fun initListener() {
        super.initListener()

        viewModel.bannerLiveData.observe(this) {
            setBanner(it)
        }

        viewModel.getArticleList().observe(this) {
            articleAdapter.setPagingData(lifecycle, it)
        }

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

        articleAdapter.addOnPagesUpdatedListener {
            viewBinding?.recyclerView?.visibility = View.VISIBLE
        }

        //列表刷新
        viewBinding?.refreshLayout?.setOnRefreshListener {
            viewBinding?.recyclerView?.swapAdapter(wrapRecyclerAdapter, true)
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

    /**
     * 设置顶部banner
     */
    private fun setBanner(list: MutableList<Banner>?){
        viewModel.hideLayoutLoading()
        homeBannerAdapter.mDatas = mutableListOf(list)
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