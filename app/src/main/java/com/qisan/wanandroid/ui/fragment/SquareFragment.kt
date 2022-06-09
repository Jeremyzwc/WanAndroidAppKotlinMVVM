package com.qisan.wanandroid.ui.fragment

import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import com.qisan.wanandroid.R
import com.qisan.wanandroid.base.BaseFragment
import com.qisan.wanandroid.databinding.FragmentSquareBinding
import com.qisan.wanandroid.vm.SquareViewModel

/**
 * Created by qisan 2022/5/25
 * com.qisan.wanandroid.ui.fragment
 */
class SquareFragment : BaseFragment<FragmentSquareBinding, SquareViewModel>() {
    override fun getLayoutId(): Int {
        return R.layout.fragment_square
    }

    override fun initData() {

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
}