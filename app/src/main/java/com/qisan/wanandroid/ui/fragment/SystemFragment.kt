package com.qisan.wanandroid.ui.fragment

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentPagerAdapter
import com.google.android.material.tabs.TabLayout
import com.qisan.baselib.adapter.CommonPageAdapter
import com.qisan.mvvm.base.fragment.BaseVMFragment
import com.qisan.wanandroid.R
import com.qisan.wanandroid.databinding.FragmentSystemBinding
import com.qisan.wanandroid.vm.HomeViewModel

/**
 * Created by qisan 2022/5/25
 * com.qisan.wanandroid.ui.fragment
 */
class SystemFragment : BaseVMFragment<FragmentSystemBinding, HomeViewModel>() {

    private val fragments = mutableListOf<Fragment>()
    private val tabList = mutableListOf<String>()

    private var systemPageAdapter: CommonPageAdapter? = null

    override fun getLayoutId(): Int {
        return R.layout.fragment_system
    }

    override fun initData() {

        tabList.add(getString(R.string.knowledge_system))
        tabList.add(getString(R.string.navigation))
        fragments.add(KnowledgeTreeFragment.newInstance())
        fragments.add(NavigationFragment.newInstance())

        systemPageAdapter = CommonPageAdapter(childFragmentManager,
            FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT,fragments,tabList)
        viewBinding?.viewPager?.adapter = systemPageAdapter
        viewBinding?.tabLayout?.setupWithViewPager(viewBinding?.viewPager)


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

        if (viewBinding?.viewPager?.currentItem == 0) {
            (systemPageAdapter?.getItem(0) as KnowledgeTreeFragment).scrollToTop()
        } else if (viewBinding?.viewPager?.currentItem == 1) {
            (systemPageAdapter?.getItem(1) as NavigationFragment).scrollToTop()
        }
    }


}