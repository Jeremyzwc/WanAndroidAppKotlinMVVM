package com.qisan.wanandroid.ui.fragment

import android.os.Bundle
import androidx.paging.LoadState
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.qisan.wanandroid.R
import com.qisan.wanandroid.adapter.FooterAdapter
import com.qisan.wanandroid.adapter.ProjectListAdapter
import com.qisan.wanandroid.base.BaseFragment
import com.qisan.wanandroid.databinding.FragmentProjectChildBinding
import com.qisan.wanandroid.vm.ProjectChildViewModel
import com.qisan.wanandroid.widget.RvItemDecoration

/**
 * Created by qisan 2022/6/15
 * com.qisan.wanandroid.ui.fragment
 */
class ProjectChildFragment : BaseFragment<FragmentProjectChildBinding, ProjectChildViewModel>() {

    companion object {
        const val CID = "cid"
        fun newInstance(cid: Int): ProjectChildFragment {
            val args = Bundle()
            args.putInt(CID, cid)
            val fragment = ProjectChildFragment()
            fragment.arguments = args
            return fragment
        }
    }

    private var cid: Int = 0

    private val mAdapter by lazy {
        ProjectListAdapter(requireActivity())
    }

    private val linearLayoutManager: LinearLayoutManager by lazy {
        LinearLayoutManager(activity)
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_project_child
    }

    override fun initData() {
        cid = requireArguments().getInt(CID,0)
        viewModel.cid = this.cid

        viewBinding?.recyclerView?.run {
            layoutManager = linearLayoutManager
            adapter = mAdapter.withLoadStateHeader(FooterAdapter())
            itemAnimator = DefaultItemAnimator()
            addItemDecoration(RvItemDecoration(context))
        }

        viewModel.getProjectList().observe(this){
            mAdapter.setPagingData(lifecycle, it)
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

        //列表刷新
        viewBinding?.refreshLayout?.setOnRefreshListener {
            viewBinding?.recyclerView?.swapAdapter(mAdapter, true)
            mAdapter.refresh()
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