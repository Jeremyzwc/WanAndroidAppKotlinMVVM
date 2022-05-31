package com.qisan.wanandroid.ui.activity

import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.qisan.wanandroid.R
import com.qisan.wanandroid.base.BaseActivity
import com.qisan.wanandroid.base.BaseFragment
import com.qisan.wanandroid.databinding.ActivityMainBinding
import com.qisan.wanandroid.ui.fragment.*
import com.qisan.wanandroid.utils.saveAs
import com.qisan.wanandroid.vm.MainViewModel
import com.qisan.wanandroid.widget.MultiplexNavigator


class MainActivity : BaseActivity<ActivityMainBinding,MainViewModel>(){

    private val homeFragment = HomeFragment()
    private val squareFragment = SquareFragment()
    private val weChatFragment = WeChatFragment()
    private val systemFragment = SystemFragment()
    private val projectFragment = ProjectFragment()

    override fun getLayoutId(): Int {
        return R.layout.activity_main
    }

    override fun initData() {

        val navHost: NavHostFragment? = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment)?.saveAs<NavHostFragment>()
        val navController = navHost?.navController
        val bottomNaviView = viewBinding?.tabNavi
        navController?.let { bottomNaviView?.setupWithNavController(it) }

        supportFragmentManager.beginTransaction().add(R.id.nav_host_fragment,homeFragment,"action_home").commit()
        supportFragmentManager.beginTransaction().add(R.id.nav_host_fragment,squareFragment,"action_square").hide(squareFragment).commit()
        supportFragmentManager.beginTransaction().add(R.id.nav_host_fragment,weChatFragment,"action_wechat").hide(weChatFragment).commit()
        supportFragmentManager.beginTransaction().add(R.id.nav_host_fragment,systemFragment,"action_system").hide(systemFragment).commit()
        supportFragmentManager.beginTransaction().add(R.id.nav_host_fragment,projectFragment,"action_project").hide(projectFragment).commit()
        var activeFragment: Fragment = homeFragment

        viewBinding?.tabNavi?.setOnItemSelectedListener {
            when (it.itemId){
                R.id.action_home -> {
                    supportFragmentManager.beginTransaction().hide(activeFragment).show(homeFragment).commit()
                    activeFragment = homeFragment
                }

                R.id.action_square -> {
                    supportFragmentManager.beginTransaction().hide(activeFragment).show(squareFragment).commit()
                    activeFragment = squareFragment
                }

                R.id.action_wechat -> {
                    supportFragmentManager.beginTransaction().hide(activeFragment).show(weChatFragment).commit()
                    activeFragment = weChatFragment
                }

                R.id.action_system -> {
                    supportFragmentManager.beginTransaction().hide(activeFragment).show(systemFragment).commit()
                    activeFragment = systemFragment
                }

                R.id.action_project -> {
                    supportFragmentManager.beginTransaction().hide(activeFragment).show(projectFragment).commit()
                    activeFragment = projectFragment
                }
            }
            return@setOnItemSelectedListener true
        }



    }
}