package com.qisan.wanandroid.ui.activity

import android.content.Context
import android.content.Intent
import android.preference.Preference
import com.qisan.wanandroid.R
import com.qisan.wanandroid.base.BaseActivity
import com.qisan.wanandroid.constant.Constant
import com.qisan.wanandroid.databinding.ActivityLoginBinding
import com.qisan.wanandroid.utils.SharePreferenceUtils
import com.qisan.wanandroid.vm.LoginViewModel

/**
 * Created by qisan 2022/6/15
 * com.qisan.wanandroid.ui.activity
 */
class LoginActivity: BaseActivity<ActivityLoginBinding, LoginViewModel>() {

    companion object{
        fun startActivity(context: Context){
            val intent = Intent(context,LoginActivity::class.java)
            context.startActivity(intent)
        }
    }

    private var user: String by SharePreferenceUtils(Constant.USERNAME_KEY, "")

    private var pwd: String by SharePreferenceUtils(Constant.PASSWORD_KEY, "")

    private var token: String by SharePreferenceUtils(Constant.TOKEN_KEY, "")

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
    }

    override fun initListener() {
        super.initListener()

        viewBinding?.btnLogin?.setOnClickListener {

        }

        viewBinding?.tvSignUp?.setOnClickListener {

        }
    }

    private fun login(){
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


    }
}