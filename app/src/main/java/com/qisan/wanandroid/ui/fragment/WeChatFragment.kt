package com.qisan.wanandroid.ui.fragment

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentPagerAdapter
import com.google.android.material.tabs.TabLayout
import com.qisan.baselib.adapter.CommonPageAdapter
import com.qisan.baselib.ext.saveAs
import com.qisan.mvvm.base.fragment.BaseVMFragment
import com.qisan.wanandroid.R
import com.qisan.wanandroid.databinding.FragmentWechatBinding
import com.qisan.wanandroid.vm.WeChatViewModel

/**
 * Created by qisan 2022/5/25
 * com.qisan.wanandroid.ui.fragment
 */
class WeChatFragment : BaseVMFragment<FragmentWechatBinding, WeChatViewModel>() {

    private val fragments: MutableList<Fragment> = mutableListOf()

    private var wxChatPageAdapter: CommonPageAdapter? = null

    override fun getLayoutId(): Int {
        return R.layout.fragment_wechat
    }

    override fun initData() {

        viewModel.getWxChapter()

    }

    override fun initListener() {
        super.initListener()

        viewModel.wxChapterLiveData.observe(this){
            val tabList: MutableList<String> = mutableListOf()
            it?.forEach { item ->
               fragments.add(WxChatChildFragment.newInstance(item.id))
                tabList.add(item.name)
            }

            wxChatPageAdapter = CommonPageAdapter(childFragmentManager,
                FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT,fragments,tabList)
            viewBinding?.viewPager?.adapter = wxChatPageAdapter
            viewBinding?.tabLayout?.setupWithViewPager(viewBinding?.viewPager)

            viewModel.hideLayoutLoading()

        }

        viewBinding?.tabLayout?.run {
            addOnTabSelectedListener(TabLayout.ViewPagerOnTabSelectedListener(viewBinding?.viewPager))
            addOnTabSelectedListener(onTabSelectedListener)
        }

        viewBinding?.viewPager?.run {
            addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(viewBinding?.tabLayout))
        }
    }


    private val onTabSelectedListener = object : TabLayout.OnTabSelectedListener {
        override fun onTabReselected(tab: TabLayout.Tab?) {
        }

        override fun onTabUnselected(tab: TabLayout.Tab?) {
        }

        override fun onTabSelected(tab: TabLayout.Tab?) {
            tab?.let {
                viewBinding?.viewPager?.setCurrentItem(it.position, false)
            }
        }
    }

    override fun scrollToTop() {
        super.scrollToTop()

        if (wxChatPageAdapter?.count == 0) {
            return
        }
        val fragment: WxChatChildFragment = viewBinding?.viewPager?.currentItem?.let { wxChatPageAdapter?.getItem(it)?.saveAs<WxChatChildFragment>() }!!
        fragment.scrollToTop()
    }
}