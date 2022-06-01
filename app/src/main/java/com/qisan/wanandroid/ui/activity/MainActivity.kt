package com.qisan.wanandroid.ui.activity

import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
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


class MainActivity : BaseActivity<ActivityMainBinding, MainViewModel>() {

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

        supportFragmentManager.beginTransaction().add(R.id.nav_host_fragment, homeFragment, "action_home").commit()
        supportFragmentManager.beginTransaction().add(R.id.nav_host_fragment, squareFragment, "action_square").setMaxLifecycle(squareFragment, Lifecycle.State.STARTED).hide(squareFragment).commit()
        supportFragmentManager.beginTransaction().add(R.id.nav_host_fragment, weChatFragment, "action_wechat").setMaxLifecycle(weChatFragment, Lifecycle.State.STARTED).hide(weChatFragment).commit()
        supportFragmentManager.beginTransaction().add(R.id.nav_host_fragment, systemFragment, "action_system").setMaxLifecycle(systemFragment, Lifecycle.State.STARTED).hide(systemFragment).commit()
        supportFragmentManager.beginTransaction().add(R.id.nav_host_fragment, projectFragment, "action_project").setMaxLifecycle(projectFragment, Lifecycle.State.STARTED).hide(projectFragment).commit()
        var activeFragment: Fragment = homeFragment
        viewBinding?.tabNavi?.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.action_home -> {
                    supportFragmentManager.beginTransaction().apply {
                        show(homeFragment)
                        setMaxLifecycle(homeFragment, Lifecycle.State.RESUMED)
                        hide(activeFragment)
                        setMaxLifecycle(activeFragment, Lifecycle.State.STARTED)
                    }.commit()
                    activeFragment = homeFragment
                }

                R.id.action_square -> {
                    supportFragmentManager.beginTransaction().apply {
                        hide(activeFragment)
                        setMaxLifecycle(activeFragment, Lifecycle.State.STARTED)
                        show(squareFragment)
                        setMaxLifecycle(squareFragment, Lifecycle.State.RESUMED)
                    }.commit()
                    activeFragment = squareFragment
                }

                R.id.action_wechat -> {
                    supportFragmentManager.beginTransaction().apply {
                        hide(activeFragment)
                        setMaxLifecycle(activeFragment, Lifecycle.State.STARTED)

                        show(weChatFragment)
                        setMaxLifecycle(weChatFragment, Lifecycle.State.RESUMED)
                    }.commit()
                    activeFragment = weChatFragment
                }

                R.id.action_system -> {
                    supportFragmentManager.beginTransaction().apply {
                        hide(activeFragment)
                        setMaxLifecycle(activeFragment, Lifecycle.State.STARTED)

                        show(systemFragment)
                        setMaxLifecycle(systemFragment, Lifecycle.State.RESUMED)
                    }.commit()
                    activeFragment = systemFragment
                }

                R.id.action_project -> {
                    supportFragmentManager.beginTransaction().apply {
                        hide(activeFragment)
                        setMaxLifecycle(activeFragment, Lifecycle.State.STARTED)

                        show(projectFragment)
                        setMaxLifecycle(projectFragment, Lifecycle.State.RESUMED)
                    }.commit()
                    activeFragment = projectFragment
                }
            }
            return@setOnItemSelectedListener true
        }
    }
}