package com.qisan.wanandroid.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.qisan.baselib.listener.ItemClickListener
import com.qisan.baselib.widget.verticaltablayout.VerticalTabLayout
import com.qisan.baselib.widget.verticaltablayout.widget.TabView
import com.qisan.mvvm.base.fragment.BaseVMFragment
import com.qisan.wanandroid.R
import com.qisan.wanandroid.adapter.NavigationAdapter
import com.qisan.wanandroid.adapter.NavigationTabAdapter
import com.qisan.wanandroid.databinding.FragmentNavigationBinding
import com.qisan.wanandroid.vm.NavigationViewModel

/**
 * Created by qisan 2022/6/14
 * com.qisan.wanandroid.ui.fragment
 */
class NavigationFragment : BaseVMFragment<FragmentNavigationBinding, NavigationViewModel>() {

    companion object {
        fun newInstance(): NavigationFragment {
            val args = Bundle()

            val fragment = NavigationFragment()
            fragment.arguments = args
            return fragment
        }
    }

    private val linearLayoutManager: LinearLayoutManager by lazy {
        LinearLayoutManager(activity)
    }

    private val navigationAdapter: NavigationAdapter by lazy {
        NavigationAdapter()
    }

    private var bScroll: Boolean = false
    private var currentIndex: Int = 0
    private var bClickTab: Boolean = false

    override fun getLayoutId(): Int {
        return R.layout.fragment_navigation
    }

    override fun initData() {
        viewBinding?.recyclerView?.run {
            layoutManager = linearLayoutManager
            adapter = navigationAdapter
            itemAnimator = DefaultItemAnimator()
            setHasFixedSize(true)
        }

        leftRightLink()

        viewModel.getNavigationData()
    }

    override fun initListener() {
        super.initListener()

        viewModel.navigationLiveData.observe(this) {
            viewBinding?.navigationTabLayout?.setTabAdapter(NavigationTabAdapter(requireActivity().applicationContext, it))

            it?.let { list ->
                navigationAdapter.mDatas = list
            }
        }

        navigationAdapter.setItemClick(object : ItemClickListener {
            override fun onItemClicked(v: View?, position: Int) {

            }
        })

    }

    //左侧tab关联recycleview
    private fun leftRightLink() {
        viewBinding?.recyclerView?.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (bScroll && (newState == RecyclerView.SCROLL_STATE_IDLE)) {
                    scrollRecyclerView()
                }
                rightLinkLeft(newState)
            }

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (bScroll) {
                    scrollRecyclerView()
                }
            }
        })

        viewBinding?.navigationTabLayout?.addOnTabSelectedListener(object : VerticalTabLayout.OnTabSelectedListener {

            override fun onTabSelected(tab: TabView?, position: Int) {
                bClickTab = true
                selectTab(position)
            }

            override fun onTabReselected(tab: TabView?, position: Int) {

            }
        })
    }

    private fun scrollRecyclerView() {
        bScroll = false
        val indexDistance: Int = currentIndex - linearLayoutManager.findFirstVisibleItemPosition()
        if (indexDistance > 0 && indexDistance < viewBinding?.recyclerView!!.childCount) {
            val top: Int = viewBinding?.recyclerView?.getChildAt(indexDistance)!!.top
            viewBinding?.recyclerView?.smoothScrollBy(0, top)
        }
    }

    /**
     * 右侧列表关联对应tab
     */
    private fun rightLinkLeft(newState: Int) {
        if (newState == RecyclerView.SCROLL_STATE_IDLE) {
            if (bClickTab) {
                bClickTab = false
                return
            }
            val firstPosition: Int = linearLayoutManager.findFirstVisibleItemPosition()
            if (firstPosition != currentIndex) {
                currentIndex = firstPosition
                setChecked(currentIndex)
            }
        }
    }


    private fun setChecked(position: Int) {
        if (bClickTab) {
            bClickTab = false
        } else {
            viewBinding?.navigationTabLayout?.setTabSelected(currentIndex)
        }
        currentIndex = position
    }

    private fun selectTab(position: Int) {
        currentIndex = position
        viewBinding?.recyclerView?.stopScroll()
        smoothScrollToPosition(position)
    }

    private fun smoothScrollToPosition(position: Int) {
        val firstPosition: Int = linearLayoutManager.findFirstVisibleItemPosition()
        val lastPosition: Int = linearLayoutManager.findLastVisibleItemPosition()
        when {
            position <= firstPosition -> {
                viewBinding?.recyclerView?.smoothScrollToPosition(position)
            }
            position <= lastPosition -> {
                val top: Int = viewBinding?.recyclerView?.getChildAt(position - firstPosition)!!.top
                viewBinding?.recyclerView?.smoothScrollBy(0, top)
            }
            else -> {
                viewBinding?.recyclerView?.smoothScrollToPosition(position)
                bScroll = true
            }
        }
    }

    override fun scrollToTop() {
        super.scrollToTop()

        viewBinding?.navigationTabLayout?.setTabSelected(0)

    }
}