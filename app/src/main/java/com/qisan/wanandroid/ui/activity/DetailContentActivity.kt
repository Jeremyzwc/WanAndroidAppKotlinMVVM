package com.qisan.wanandroid.ui.activity

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.KeyEvent
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.webkit.WebSettings
import android.webkit.WebView
import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.google.android.material.appbar.AppBarLayout
import com.just.agentweb.AgentWeb
import com.just.agentweb.WebChromeClient
import com.qisan.baselib.utils.ToastUtils
import com.qisan.mvvm.base.activity.BaseVMActivity
import com.qisan.wanandroid.R
import com.qisan.wanandroid.constant.Constant
import com.qisan.wanandroid.databinding.ActivityDetailContentBinding
import com.qisan.wanandroid.ext.getAgentWeb
import com.qisan.wanandroid.global.WanUser
import com.qisan.wanandroid.vm.DetailContentViewModel
import com.qisan.baselib.webclient.WebClientFactory
import com.qisan.wanandroid.room.ScanRecordEnity
import com.qisan.wanandroid.widget.NestedScrollAgentWebView

/**
 * Created by qisan 2022/6/16
 * com.qisan.wanandroid.ui.activity
 */
class DetailContentActivity: BaseVMActivity<ActivityDetailContentBinding, DetailContentViewModel>() {

    private var mAgentWeb: AgentWeb? = null

    private var shareTitle: String = ""
    private var shareUrl: String = ""
    private var shareId: Int = -1

    companion object {

        fun startActivity(context: Context?, id: Int, title: String, url: String, bundle: Bundle? = null) {
            Intent(context, DetailContentActivity::class.java).run {
                putExtra(Constant.CONTENT_ID_KEY, id)
                putExtra(Constant.CONTENT_TITLE_KEY, title)
                putExtra(Constant.CONTENT_URL_KEY, url)
                context?.startActivity(this, bundle)
            }
        }

        fun startActivity(context: Context?, url: String) {
            startActivity(context, -1, "", url)
        }

    }

    override fun getLayoutId(): Int {
        return R.layout.activity_detail_content
    }

    override fun initData() {

        intent.extras?.let {
            shareId = it.getInt(Constant.CONTENT_ID_KEY, -1)
            shareTitle = it.getString(Constant.CONTENT_TITLE_KEY, "")
            shareUrl = it.getString(Constant.CONTENT_URL_KEY, "")

            viewModel.recordScanContent(ScanRecordEnity(shareId,shareTitle,shareUrl))
        }

        viewBinding?.toolbarLayout?.toolbar.apply {
            setSupportActionBar(this)
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }

        initWebView()
    }

    /**
     * 初始化webView
     */
    @SuppressLint("SetJavaScriptEnabled")
    private fun initWebView() {

        val webView = NestedScrollAgentWebView(this)

        val layoutParams = CoordinatorLayout.LayoutParams(-1, -1)
        layoutParams.behavior = AppBarLayout.ScrollingViewBehavior()

        mAgentWeb = viewBinding?.clMain?.let {
            shareUrl.getAgentWeb(
                this,
                it,
                layoutParams,
                webView,
                WebClientFactory.create(shareUrl),
                mWebChromeClient,
                mThemeColor)
        }
        mAgentWeb?.webCreator?.webView?.apply {
            overScrollMode = WebView.OVER_SCROLL_NEVER
            settings.domStorageEnabled = true
            settings.javaScriptEnabled = true
            settings.loadsImagesAutomatically = true
            settings.useWideViewPort = true
            settings.loadWithOverviewMode = true
            settings.layoutAlgorithm = WebSettings.LayoutAlgorithm.SINGLE_COLUMN
            settings.mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW
        }
    }

    private val mWebChromeClient = object : WebChromeClient() {
        override fun onReceivedTitle(view: WebView, title: String) {
            super.onReceivedTitle(view, title)
            viewBinding?.toolbarLayout?.tvTitle?.run {
                visibility = View.VISIBLE
                text = title
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_content, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_share -> {
                Intent().run {
                    action = Intent.ACTION_SEND
                    putExtra(Intent.EXTRA_TEXT, getString(
                        R.string.share_article_url,
                        getString(R.string.app_name), shareTitle, shareUrl
                    ))
                    type = Constant.CONTENT_SHARE_TYPE
                    startActivity(Intent.createChooser(this, getString(R.string.action_share)))
                }
                return true
            }
            R.id.action_like -> {
                if (WanUser.isLogin()) {
                    if (shareId == -1) return true
                    viewModel.addCollectArticle(shareId)
                } else {
                    Intent(this, LoginActivity::class.java).run {
                        startActivity(this)
                    }
                    ToastUtils.show(resources.getString(R.string.login_tint))
                }
                return true
            }
            R.id.action_browser -> {
                Intent().run {
                    action = "android.intent.action.VIEW"
                    data = Uri.parse(shareUrl)
                    startActivity(this)
                }
                return true
            }

            android.R.id.home -> {
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        mAgentWeb?.let {
            if (!it.back()) {
                super.onBackPressed()
            }
        }
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        if (mAgentWeb?.handleKeyEvent(keyCode, event)!!) {
            return true
        }
        return super.onKeyDown(keyCode, event)
    }

    override fun onResume() {
        mAgentWeb?.webLifeCycle?.onResume()
        super.onResume()
    }

    override fun onPause() {
        mAgentWeb?.webLifeCycle?.onPause()
        super.onPause()
    }

    override fun onDestroy() {
        mAgentWeb?.webLifeCycle?.onDestroy()
        super.onDestroy()
    }
}