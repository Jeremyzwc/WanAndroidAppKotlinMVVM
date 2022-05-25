package com.qisan.wanandroid.ui.activity

import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.qisan.wanandroid.R
import com.qisan.wanandroid.base.BaseActivity
import com.qisan.wanandroid.databinding.ActivityMainBinding
import com.qisan.wanandroid.utils.saveAs
import com.qisan.wanandroid.vm.MainViewModel

class MainActivity : BaseActivity<ActivityMainBinding,MainViewModel>(){

    override fun getLayoutId(): Int {
        return R.layout.activity_main
    }

    override fun initData() {

        val navHost: NavHostFragment? = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment)?.saveAs<NavHostFragment>()
        val navController = navHost?.navController
        val bottomNaviView = viewDataBinding.tabNavi
        navController?.let { bottomNaviView.setupWithNavController(it) }

    }
}