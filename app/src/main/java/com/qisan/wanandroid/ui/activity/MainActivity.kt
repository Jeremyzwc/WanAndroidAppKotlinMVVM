package com.qisan.wanandroid.ui.activity

import android.annotation.SuppressLint
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.navigation.NavigationView
import com.jeremyliao.liveeventbus.LiveEventBus
import com.qisan.wanandroid.R
import com.qisan.wanandroid.base.BaseActivity
import com.qisan.wanandroid.databinding.ActivityMainBinding
import com.qisan.wanandroid.databinding.NavHeaderMainBinding
import com.qisan.wanandroid.event.LoginEvent
import com.qisan.wanandroid.global.WanUser
import com.qisan.wanandroid.ui.fragment.*
import com.qisan.wanandroid.utils.DialogUtil
import com.qisan.wanandroid.utils.SettingUtil
import com.qisan.wanandroid.utils.ToastUtils
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

    //当前展示的fragment
    private var activeFragment: Fragment = homeFragment

    private var navHeaderMainBinding: NavHeaderMainBinding? = null

    private val tvUserId: TextView? by lazy {
        viewBinding?.navView?.getHeaderView(0)?.findViewById(R.id.tv_user_id)
    }

    private val tvUserName: TextView? by lazy {
        viewBinding?.navView?.getHeaderView(0)?.findViewById(R.id.tv_username)
    }
    private val tvUserGrade: TextView? by lazy {
        viewBinding?.navView?.getHeaderView(0)?.findViewById(R.id.tv_user_grade)
    }
    private val tvUserRank: TextView? by lazy {
        viewBinding?.navView?.getHeaderView(0)?.findViewById(R.id.tv_user_rank)
    }

    private val tvScore: TextView? by lazy {
        viewBinding?.navView?.getHeaderView(0)?.findViewById(R.id.nav_score)
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_main
    }

    override fun initData() {

        navHeaderMainBinding = NavHeaderMainBinding.inflate(layoutInflater)

        viewBinding?.toolbarLayout?.toolbar.run {
            title = getString(R.string.app_name)
            setSupportActionBar(this)
        }

        initDrawerLayout()

        initMainNav()

        val navHost: NavHostFragment? = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment)?.saveAs<NavHostFragment>()
        val navController = navHost?.navController
        val bottomNaviView = viewBinding?.tabNavi
        navController?.let { bottomNaviView?.setupWithNavController(it) }

        initFragmentAdd()

        initLiveDataBus()

    }

    @SuppressLint("SetTextI18n")
    override fun initListener() {
        super.initListener()

        viewBinding?.floatingAction?.setOnClickListener {
            fabClickInto()
        }
    }

    private fun initFragmentAdd() {

        initLazyFragment()

        viewBinding?.tabNavi?.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.action_home -> {
                    showFragment(homeFragment)
                    mIndex = FRAGMENT_HOME
                    viewBinding?.toolbarLayout?.toolbar?.title = getString(R.string.home)
                }

                R.id.action_square -> {
                    showFragment(squareFragment)
                    mIndex = FRAGMENT_SQUARE
                    viewBinding?.toolbarLayout?.toolbar?.title = getString(R.string.square)
                }

                R.id.action_wechat -> {
                    showFragment(weChatFragment)
                    mIndex = FRAGMENT_WECHAT
                    viewBinding?.toolbarLayout?.toolbar?.title = getString(R.string.wechat)
                }

                R.id.action_system -> {
                    showFragment(systemFragment)
                    mIndex = FRAGMENT_SYSTEM
                    viewBinding?.toolbarLayout?.toolbar?.title = getString(R.string.knowledge_system)
                }

                R.id.action_project -> {
                    showFragment(projectFragment)
                    mIndex = FRAGMENT_PROJECT
                    viewBinding?.toolbarLayout?.toolbar?.title = getString(R.string.project)
                }
            }
            return@setOnItemSelectedListener true
        }
    }

    //通过setMaxLifecycle让Fragment可以懒加载实现
    private fun initLazyFragment() {
        supportFragmentManager.beginTransaction().add(R.id.nav_host_fragment, homeFragment, "action_home").commit()
        supportFragmentManager.beginTransaction().add(R.id.nav_host_fragment, squareFragment, "action_square").setMaxLifecycle(squareFragment, Lifecycle.State.STARTED).hide(squareFragment).commit()
        supportFragmentManager.beginTransaction().add(R.id.nav_host_fragment, weChatFragment, "action_wechat").setMaxLifecycle(weChatFragment, Lifecycle.State.STARTED).hide(weChatFragment).commit()
        supportFragmentManager.beginTransaction().add(R.id.nav_host_fragment, systemFragment, "action_system").setMaxLifecycle(systemFragment, Lifecycle.State.STARTED).hide(systemFragment).commit()
        supportFragmentManager.beginTransaction().add(R.id.nav_host_fragment, projectFragment, "action_project").setMaxLifecycle(projectFragment, Lifecycle.State.STARTED).hide(projectFragment).commit()
    }

    private fun showFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().apply {
            hide(activeFragment)
            setMaxLifecycle(activeFragment, Lifecycle.State.STARTED)
            show(fragment)
            setMaxLifecycle(fragment, Lifecycle.State.RESUMED)
        }.commit()
        activeFragment = fragment
    }

    private fun initMainNav() {
        viewBinding?.navView?.run {
            setNavigationItemSelectedListener(drawerNavigationItemSelectedListener)

            menu.findItem(R.id.nav_logout).isVisible = WanUser.isLogin()
        }

        tvUserName?.run {
            text = if (!WanUser.isLogin()) getString(R.string.go_login) else WanUser.getLogin()?.username
            setOnClickListener {
                if (!WanUser.isLogin()) {
                    LoginActivity.startActivity(this@MainActivity)
                }
            }
        }
    }

    //事件通信监听
    @SuppressLint("SetTextI18n")
    private fun initLiveDataBus() {
        //登录监听回调
        LiveEventBus.get(LoginEvent::class.java)
            .observe(this) {
                setUserInfo(it.isLogin)
            }


        viewModel.userInfoLiveData.observe(this) {
            tvUserId?.text = it?.userId.toString()
            tvUserGrade?.text = ((it?.coinCount?.div(100) ?: 0) + 1).toString()
            tvUserRank?.text = it?.rank.toString()
            tvScore?.text = it?.coinCount.toString()
        }

        viewModel.logoutLiveData.observe(this){
            setUserInfo(false)
        }
    }

    private fun setUserInfo(isLogin: Boolean){
        if (isLogin) {
            tvUserName?.text = WanUser.loginInfo?.username
            viewBinding?.navView?.menu?.findItem(R.id.nav_logout)?.isVisible = true
            viewModel.getUserInfo()
        } else {
            tvUserName?.text = resources.getString(R.string.go_login)
            viewBinding?.navView?.menu?.findItem(R.id.nav_logout)?.isVisible = false
            // 重置用户信息
            tvUserId?.text = getString(R.string.nav_line_4)
            tvUserGrade?.text = getString(R.string.nav_line_2)
            tvUserRank?.text = getString(R.string.nav_line_2)
            tvScore?.text = ""
        }
    }

    private fun fabClickInto() {
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
    private fun initDrawerLayout() {
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

    //toolbar上的搜索
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

    private val drawerNavigationItemSelectedListener = NavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.nav_score -> {
                if (WanUser.isLogin()) {
//                    Intent(this@MainActivity, ScoreActivity::class.java).run {
//                        startActivity(this)
//                    }
                } else {
                    ToastUtils.show(resources.getString(R.string.login_tint))
//                    goLogin()
                }
            }
            R.id.nav_collect -> {
                if (WanUser.isLogin()) {
//                    goCommonActivity(Constant.Type.COLLECT_TYPE_KEY)
                } else {
                    ToastUtils.show(resources.getString(R.string.login_tint))
//                    goLogin()
                }
            }
            R.id.nav_share -> {
                if (WanUser.isLogin()) {
//                    startActivity(Intent(this, ShareActivity::class.java))
                } else {
                    ToastUtils.show(resources.getString(R.string.login_tint))
//                    goLogin()
                }
            }
            R.id.nav_setting -> {
//                Intent(this@MainActivity, SettingActivity::class.java).run {
//                    startActivity(this)
//                }
            }
            R.id.nav_logout -> {

                DialogUtil.getConfirmDialog(this, resources.getString(R.string.confirm_logout)) { _, _ ->
                    viewModel.logout(resources.getString(R.string.logout_ing))
                }.show()
            }
            R.id.nav_night_mode -> {
                if (SettingUtil.getIsNightMode()) {
                    SettingUtil.setIsNightMode(false)
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                } else {
                    SettingUtil.setIsNightMode(true)
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                }
                window.setWindowAnimations(R.style.WindowAnimationFadeInOut)
                recreate()
            }
            R.id.nav_todo -> {
                if (WanUser.isLogin()) {
//                    Intent(this@MainActivity, TodoActivity::class.java).run {
//                        startActivity(this)
//                    }
                } else {
                    ToastUtils.show(resources.getString(R.string.login_tint))
//                    goLogin()
                }
            }
        }
        true
    }



    override fun recreate() {
        try {
            val fragmentTransaction = supportFragmentManager.beginTransaction()
            fragmentTransaction.remove(homeFragment)
            fragmentTransaction.remove(squareFragment)
            fragmentTransaction.remove(systemFragment)
            fragmentTransaction.remove(projectFragment)
            fragmentTransaction.remove(weChatFragment)
            fragmentTransaction.commitAllowingStateLoss()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        super.recreate()
    }
}