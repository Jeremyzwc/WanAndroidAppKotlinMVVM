package com.qisan.wanandroid.ui.fragment

import android.annotation.SuppressLint
import android.view.View
import android.widget.ImageView
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import cn.bingoogolapple.bgabanner.BGABanner
import com.qisan.wanandroid.R
import com.qisan.wanandroid.adapter.ArticleAdapter
import com.qisan.wanandroid.adapter.HomeBannerAdapter
import com.qisan.wanandroid.adapter.WrapRecyclerAdapter
import com.qisan.wanandroid.base.BaseFragment
import com.qisan.wanandroid.databinding.FragmentHomeBinding
import com.qisan.wanandroid.databinding.ItemHomeBannerBinding
import com.qisan.wanandroid.utils.GlideUtils
import com.qisan.wanandroid.vm.HomeViewModel
import com.qisan.wanandroid.widget.RvItemDecoration
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * Created by qisan 2022/5/25
 * com.qisan.wanandroid.ui.fragment
 */
class HomeFragment : BaseFragment<FragmentHomeBinding, HomeViewModel>() {

    private var bannerView: View? = null
    private var bannerBinding: ItemHomeBannerBinding? = null

    private val wrapRecyclerAdapter by lazy { WrapRecyclerAdapter(mAdapter) }

    private val mAdapter by lazy {
        ArticleAdapter()
    }

    private val bannerAdapter: BGABanner.Adapter<ImageView, String> by lazy {
        BGABanner.Adapter<ImageView, String> { _, imageView, feedImageUrl, _ ->
            GlideUtils.load(activity, feedImageUrl, imageView)
        }
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_home
    }

    @SuppressLint("InflateParams")
    override fun initData() {

        //初始化bannerView
        bannerBinding = ItemHomeBannerBinding.inflate(layoutInflater)
        bannerBinding?.banner?.run {
            setDelegate(bannerDelegate)
        }

        viewBinding?.recyclerView?.run {
            layoutManager = LinearLayoutManager(activity)
            adapter = wrapRecyclerAdapter
            itemAnimator = DefaultItemAnimator()
            addItemDecoration(RvItemDecoration(context))
        }
    }


    override fun initListener() {
        super.initListener()

        viewModel.bannerLiveData.observe(this) {
            val bannerFeedList = ArrayList<String>()
            val bannerTitleList = ArrayList<String>()
            it?.forEach { banner ->
                bannerFeedList.add(banner.imagePath)
                bannerTitleList.add(banner.title)
            }

            bannerBinding?.banner?.run {
                setAutoPlayAble(bannerFeedList.size > 1)
                setData(bannerFeedList, bannerTitleList)
                setAdapter(bannerAdapter)
            }
            lifecycleScope.launch {
                delay(150)
                viewModel.hideLayoutLoading()
                wrapRecyclerAdapter.addHeaderView(bannerBinding?.root)

            }
        }

        viewModel.getArticleList().observe(this) {
            mAdapter.setPagingData(lifecycle, it)
            viewModel.getBanner()
        }

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
            viewModel.getBanner()
            viewBinding?.recyclerView?.swapAdapter(wrapRecyclerAdapter, true)
            mAdapter.refresh()
        }
    }


    private val bannerDelegate = BGABanner.Delegate<ImageView, String> { banner, imageView, model, position ->
//        if (bannerDatas.size > 0) {
//            val data = bannerDatas[position]
//            ContentActivity.start(activity, data.id, data.title, data.url)
//        }
    }
}