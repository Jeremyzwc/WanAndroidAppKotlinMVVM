package com.qisan.wanandroid.ui.fragment

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentPagerAdapter
import com.google.android.material.tabs.TabLayout
import com.qisan.wanandroid.R
import com.qisan.wanandroid.adapter.CommonPageAdapter
import com.qisan.wanandroid.base.BaseFragment
import com.qisan.wanandroid.databinding.FragmentHomeBinding
import com.qisan.wanandroid.databinding.FragmentProjcetBinding
import com.qisan.wanandroid.utils.saveAs
import com.qisan.wanandroid.vm.ProjectViewModel

/**
 * Created by qisan 2022/5/25
 * com.qisan.wanandroid.ui.fragment
 */
class ProjectFragment : BaseFragment<FragmentProjcetBinding, ProjectViewModel>() {


    private val fragments: MutableList<Fragment> = mutableListOf()

    private var projectAdapter: CommonPageAdapter? = null

    override fun getLayoutId(): Int {
        return R.layout.fragment_projcet
    }

    override fun initData() {
        viewModel.getProjectTreeList()

        viewModel.projectTreeLiveData.observe(this){
            val tabList: MutableList<String> = mutableListOf()
            it?.forEach { item ->
                fragments.add(ProjectChildFragment.newInstance(item.id))
                tabList.add(item.name)
            }

            projectAdapter = CommonPageAdapter(childFragmentManager,
                FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT,fragments,tabList)
            viewBinding?.viewPager?.adapter = projectAdapter
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

        if (projectAdapter?.count == 0) {
            return
        }
        val fragment: ProjectChildFragment = viewBinding?.viewPager?.currentItem?.let { projectAdapter?.getItem(it)?.saveAs<ProjectChildFragment>() }!!
        fragment.scrollToTop()
    }
}