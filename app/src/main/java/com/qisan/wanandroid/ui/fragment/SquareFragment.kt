package com.qisan.wanandroid.ui.fragment

import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.paging.LoadState
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.qisan.mvvm.base.fragment.BaseVMFragment
import com.qisan.wanandroid.R
import com.qisan.wanandroid.adapter.ArticleAdapter
import com.qisan.wanandroid.adapter.FooterAdapter
import com.qisan.wanandroid.databinding.FragmentSquareBinding
import com.qisan.baselib.listener.ItemClickListener
import com.qisan.wanandroid.ui.activity.DetailContentActivity
import com.qisan.wanandroid.vm.SquareViewModel
import com.qisan.wanandroid.widget.RvItemDecoration

/**
 * Created by qisan 2022/5/25
 * com.qisan.wanandroid.ui.fragment
 */
class SquareFragment : BaseVMFragment<FragmentSquareBinding, SquareViewModel>() {

    private val articleAdapter by lazy {
        ArticleAdapter(requireActivity())
    }

    private val linearLayoutManager: LinearLayoutManager by lazy {
        LinearLayoutManager(activity)
    }


    override fun getLayoutId(): Int {
        return R.layout.fragment_square
    }

    override fun initData() {

        viewBinding?.recyclerView?.run {
            articleAdapter.setVm(viewModel)
            layoutManager = linearLayoutManager
            adapter = articleAdapter.withLoadStateHeader(FooterAdapter())
            itemAnimator = DefaultItemAnimator()
            addItemDecoration(RvItemDecoration(context))
        }

        viewModel.getSquareList().observe(this){
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

    override fun initMenu() {
        setHasOptionsMenu(true)
        super.initMenu()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_share, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_add -> {
//                Intent(activity, CommonActivity::class.java).run {
//                    putExtra(Constant.TYPE_KEY, Constant.Type.SHARE_ARTICLE_TYPE_KEY)
//                    startActivity(this)
//                }
            }
        }
        return super.onOptionsItemSelected(item)
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