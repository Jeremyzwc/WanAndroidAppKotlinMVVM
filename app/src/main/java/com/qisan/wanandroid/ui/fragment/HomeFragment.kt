package com.qisan.wanandroid.ui.fragment

import android.util.Log
import com.qisan.wanandroid.R
import com.qisan.wanandroid.base.BaseFragment
import com.qisan.wanandroid.databinding.FragmentHomeBinding
import com.qisan.wanandroid.vm.HomeViewModel

/**
 * Created by qisan 2022/5/25
 * com.qisan.wanandroid.ui.fragment
 */
class HomeFragment : BaseFragment<FragmentHomeBinding,HomeViewModel>() {

    override fun getLayoutId(): Int {
        return R.layout.fragment_home
    }

    override fun initData() {

//        viewDataBinding.isShowLoadingLayout = isShowLoadingLayout

        viewModel.getArticleList()

        viewModel.articleLiveData.observe(this){
            Log.e("",it.toString())
        }
    }

    override fun showLoading() {
        super.showLoading()
//        viewDataBinding.isShowLoadingLayout = true
    }
}