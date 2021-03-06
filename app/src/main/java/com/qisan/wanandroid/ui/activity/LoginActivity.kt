package com.qisan.wanandroid.ui.activity

import android.content.Context
import android.content.Intent
import android.view.MenuItem
import com.google.gson.Gson
import com.jeremyliao.liveeventbus.LiveEventBus
import com.qisan.baselib.event.LoginEvent
import com.qisan.baselib.utils.SharePreferenceUtils
import com.qisan.baselib.utils.ToastUtils
import com.qisan.mvvm.base.activity.BaseVMActivity
import com.qisan.wanandroid.R
import com.qisan.wanandroid.constant.Constant
import com.qisan.wanandroid.databinding.ActivityLoginBinding
import com.qisan.wanandroid.global.WanUser
import com.qisan.wanandroid.vm.LoginViewModel

/**
 * Created by qisan 2022/6/15
 * com.qisan.wanandroid.ui.activity
 */
class LoginActivity : BaseVMActivity<ActivityLoginBinding, LoginViewModel>() {

    companion object {
        fun startActivity(context: Context) {
            val intent = Intent(context, LoginActivity::class.java)
            context.startActivity(intent)
        }
    }

    private var user: String by SharePreferenceUtils(Constant.USERNAME_KEY, "")

    private var pwd: String by SharePreferenceUtils(Constant.PASSWORD_KEY, "")

    private var token: String by SharePreferenceUtils(Constant.TOKEN_KEY, "")

    private var loginInfoStr: String by SharePreferenceUtils(Constant.LOGIN_INFO, "")

    override fun getLayoutId(): Int {
        return R.layout.activity_login
    }

    override fun initData() {

        viewBinding?.etUsername?.setText(user)
        viewBinding?.toolbarLayout?.toolbar?.run {
            title = resources.getString(R.string.login)
            setSupportActionBar(this)
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }

        viewModel.loginInfoLiveData.observe(this) {
            ToastUtils.show(getString(R.string.login_success))
            user = it?.username.toString()
            pwd = it?.password.toString()
            token = it?.token.toString()

            loginInfoStr = Gson().toJson(it)
            WanUser.loginInfo = it
            LiveEventBus
                .get(LoginEvent::class.java)
                .post(LoginEvent(true))
            finish()
        }
    }

    override fun initListener() {
        super.initListener()

        viewBinding?.btnLogin?.setOnClickListener {
            login()
        }

        viewBinding?.tvSignUp?.setOnClickListener {

        }


    }

    private fun login() {
        val username: String = viewBinding?.etUsername?.text.toString()
        val password: String = viewBinding?.etPassword?.text.toString()

        if (username.isEmpty()) {
            viewBinding?.etUsername?.error = getString(R.string.username_not_empty)
            return
        }
        if (password.isEmpty()) {
            viewBinding?.etPassword?.error = getString(R.string.password_not_empty)
            return
        }

        viewModel.login(username, password)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }
}