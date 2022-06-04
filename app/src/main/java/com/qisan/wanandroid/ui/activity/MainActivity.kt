package com.qisan.wanandroid.ui.activity

import android.content.Intent
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.qisan.wanandroid.R
import com.qisan.wanandroid.base.BaseActivity
import com.qisan.wanandroid.databinding.ActivityMainBinding
import com.qisan.wanandroid.ui.fragment.*
import com.qisan.wanandroid.utils.saveAs
import com.qisan.wanandroid.vm.MainViewModel


class MainActivity : BaseActivity<ActivityMainBinding, MainViewModel>() {

    private val homeFragment = HomeFragment()
    private val squareFragment = SquareFragment()
    private val weChatFragment = WeChatFragment()
    private val systemFragment = SystemFragment()
    private val projectFragment = ProjectFragment()

    private val FRAGMENT_HOME = 0x01
    private val FRAGMENT_SQUARE = 0x02
    private val FRAGMENT_WECHAT = 0x03
    private val FRAGMENT_SYSTEM = 0x04
    private val FRAGMENT_PROJECT = 0x05

    private var mIndex = FRAGMENT_HOME

    private var activeFragment: Fragment = homeFragment

    override fun getLayoutId(): Int {
        return R.layout.activity_main
    }

    override fun initData() {

        viewBinding?.toolbarLayout?.toolbar.run {
            title = getString(R.string.app_name)
            setSupportActionBar(this)
        }

        initDrawerLayout()

        val navHost: NavHostFragment? = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment)?.saveAs<NavHostFragment>()
        val navController = navHost?.navController
        val bottomNaviView = viewBinding?.tabNavi
        navController?.let { bottomNaviView?.setupWithNavController(it) }

        initLazyFragment()
    }

    override fun initListener() {
        super.initListener()

        viewBinding?.floatingAction?.setOnClickListener {
            fabClickInto()
        }
    }

    //让Fragment可以懒加载实现
    private fun initLazyFragment() {
        supportFragmentManager.beginTransaction().add(R.id.nav_host_fragment, homeFragment, "action_home").commit()
        supportFragmentManager.beginTransaction().add(R.id.nav_host_fragment, squareFragment, "action_square").setMaxLifecycle(squareFragment, Lifecycle.State.STARTED).hide(squareFragment).commit()
        supportFragmentManager.beginTransaction().add(R.id.nav_host_fragment, weChatFragment, "action_wechat").setMaxLifecycle(weChatFragment, Lifecycle.State.STARTED).hide(weChatFragment).commit()
        supportFragmentManager.beginTransaction().add(R.id.nav_host_fragment, systemFragment, "action_system").setMaxLifecycle(systemFragment, Lifecycle.State.STARTED).hide(systemFragment).commit()
        supportFragmentManager.beginTransaction().add(R.id.nav_host_fragment, projectFragment, "action_project").setMaxLifecycle(projectFragment, Lifecycle.State.STARTED).hide(projectFragment).commit()
        viewBinding?.tabNavi?.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.action_home -> {
                    showFragment(homeFragment)
                    mIndex = FRAGMENT_HOME
                }

                R.id.action_square -> {
                    showFragment(squareFragment)
                    mIndex = FRAGMENT_SQUARE
                }

                R.id.action_wechat -> {
                    showFragment(weChatFragment)
                    mIndex = FRAGMENT_WECHAT
                }

                R.id.action_system -> {
                    showFragment(systemFragment)
                    mIndex = FRAGMENT_SYSTEM
                }

                R.id.action_project -> {
                    showFragment(projectFragment)
                    mIndex = FRAGMENT_PROJECT
                }
            }
            return@setOnItemSelectedListener true
        }
    }

    private fun showFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().apply {
            hide(activeFragment)
            setMaxLifecycle(activeFragment, Lifecycle.State.STARTED)
            show(fragment)
            setMaxLifecycle(projectFragment, Lifecycle.State.RESUMED)
        }.commit()
        activeFragment = fragment
    }

    private fun fabClickInto(){
        when (mIndex) {
            FRAGMENT_HOME -> {
                homeFragment.scrollToTop()
            }
            FRAGMENT_SQUARE -> {
                squareFragment.scrollToTop()
            }
            FRAGMENT_SYSTEM -> {
                systemFragment.scrollToTop()
            }
            FRAGMENT_PROJECT -> {
                projectFragment.scrollToTop()
            }
            FRAGMENT_WECHAT -> {
                weChatFragment.scrollToTop()
            }
        }
    }

    //添加tool菜单侧滑按钮已经打开关闭监听
    private fun initDrawerLayout(){
        viewBinding?.drawerLayout?.run {
            val toggle = ActionBarDrawerToggle(
                this@MainActivity,
                this,
                viewBinding?.toolbarLayout?.toolbar, R.string.navigation_drawer_open,
                R.string.navigation_drawer_close
            )
            addDrawerListener(toggle)
            toggle.syncState()
        }
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        if (mIndex != FRAGMENT_SQUARE) {
            menuInflater.inflate(R.menu.menu_main_search, menu)
        }
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_search -> {
//                Intent(this, SearchActivity::class.java).run {
//                    startActivity(this)
//                }
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}