package com.qisan.wanandroid.widget.verticaltablayout.widget

import android.R
import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.os.Build
import android.text.TextUtils
import android.view.Gravity
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.Px
import com.qisan.wanandroid.widget.verticaltablayout.widget.ITabView.*
import q.rorbin.badgeview.Badge
import q.rorbin.badgeview.DisplayUtil

/**
 * Created by QiSan 2022/6/18
 * package com.qisan.wanandroid.widget.verticaltablayout.widget
 */
class QTabView(context: Context) : TabView(context) {

    private var mContext: Context? = context
    private var mTitle: TextView? = null
    private var mBadgeView: Badge? = null
    private var mTabIcon: TabIcon? = null
    private var mTabTitle: TabTitle? = null
    private var mTabBadge: TabBadge? = null
    private var mChecked = false
    private var mDefaultBackground: Drawable? = null


    init {
        mTabIcon = TabIcon.Builder().build()
        mTabTitle = TabTitle.Builder().build()
        mTabBadge = TabBadge.Builder().build()
        initView()
        val attrs: IntArray = intArrayOf(R.attr.selectableItemBackgroundBorderless)
        val a = mContext!!.theme.obtainStyledAttributes(attrs)
        mDefaultBackground = a.getDrawable(0)
        a.recycle()
        setDefaultBackground()
    }

    private fun initView() {
        minimumHeight = DisplayUtil.dp2px(mContext, 25f)
        if (mTitle == null) {
            mTitle = TextView(mContext)
            val params = LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT)
            params.gravity = Gravity.CENTER
            mTitle?.layoutParams = params
            this.addView(mTitle)
        }
        initTitleView()
        initIconView()
        initBadge()
    }

    override fun setPaddingRelative(@Px start: Int, @Px top: Int, @Px end: Int, @Px bottom: Int) {
        mTitle?.setPaddingRelative(start, top, end, bottom)
    }

    override fun setPadding(@Px left: Int, @Px top: Int, @Px right: Int, @Px bottom: Int) {
        mTitle!!.setPadding(left, top, right, bottom)
    }

    private fun initBadge() {
        mBadgeView = TabBadgeView.bindTab(this)
        if(mBadgeView == null){
            return
        }
        if (mTabBadge?.getBackgroundColor() != -0x17b1c0) {
            mBadgeView?.badgeBackgroundColor = mTabBadge?.getBackgroundColor()!!
        }
        if (mTabBadge?.getBadgeTextColor() != -0x1) {
            mBadgeView?.badgeTextColor = mTabBadge?.getBadgeTextColor()!!
        }
        if (mTabBadge?.getStrokeColor() != Color.TRANSPARENT || mTabBadge?.getStrokeWidth() != 0f) {
            mTabBadge?.getStrokeWidth()?.let { mBadgeView?.stroke(mTabBadge?.getStrokeColor()!!, it, true) }
        }
        if (mTabBadge?.getDrawableBackground() != null || mTabBadge?.isDrawableBackgroundClip() == true) {
            mTabBadge?.isDrawableBackgroundClip()?.let { mBadgeView?.setBadgeBackground(mTabBadge?.getDrawableBackground(), it) }
        }
        if (mTabBadge?.getBadgeTextSize() != 11f) {
            mTabBadge?.getBadgeTextSize()?.let { mBadgeView?.setBadgeTextSize(it, true) }
        }
        if (mTabBadge?.getBadgePadding() != 5f) {
            mTabBadge?.getBadgePadding()?.let { mBadgeView?.setBadgePadding(it, true) }
        }
        if (mTabBadge?.getBadgeNumber() != 0) {
            mBadgeView?.badgeNumber = mTabBadge!!.getBadgeNumber()
        }
        if (mTabBadge?.getBadgeText() != null) {
            mBadgeView?.badgeText = mTabBadge?.getBadgeText()
        }
        if (mTabBadge?.getBadgeGravity() != Gravity.END or Gravity.TOP) {
            mBadgeView?.badgeGravity = mTabBadge?.getBadgeGravity()!!
        }
        if (mTabBadge?.getGravityOffsetX() != 5 || mTabBadge?.getGravityOffsetY() != 5) {
            mBadgeView?.setGravityOffset(mTabBadge?.getGravityOffsetX()!!.toFloat(), mTabBadge?.getGravityOffsetY()!!.toFloat(), true)
        }
        if (mTabBadge?.isExactMode() == true) {
            mBadgeView?.isExactMode = mTabBadge?.isExactMode()!!
        }
        if (!mTabBadge?.isShowShadow()!!) {
            mBadgeView?.isShowShadow = mTabBadge!!.isShowShadow()
        }
        if (mTabBadge?.getOnDragStateChangedListener() != null) {
            mBadgeView?.setOnDragStateChangedListener(mTabBadge?.getOnDragStateChangedListener())
        }
    }

    private fun initTitleView() {
        (if (isChecked) mTabTitle?.getColorSelected() else mTabTitle?.getColorNormal())?.let { mTitle?.setTextColor(it) }
        mTitle?.textSize = mTabTitle?.getTitleTextSize()?.toFloat()!!
        mTitle?.text = mTabTitle?.getContent()
        mTitle?.gravity = Gravity.CENTER
        mTitle?.ellipsize = TextUtils.TruncateAt.END
        refreshDrawablePadding()
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private fun initIconView() {
        val iconResid = if (mChecked) mTabIcon!!.getSelectedIcon() else mTabIcon!!.getNormalIcon()
        var drawable: Drawable? = null
        if (iconResid != 0) {
            drawable = mContext?.resources?.getDrawable(iconResid)
            val r = if (mTabIcon!!.getIconWidth() != -1) mTabIcon!!.getIconWidth() else drawable?.intrinsicWidth
            val b = if (mTabIcon!!.getIconHeight() != -1) mTabIcon!!.getIconHeight() else drawable?.intrinsicHeight
            drawable?.setBounds(0, 0, r!!, b!!)
        }
        when (mTabIcon!!.getIconGravity()) {
            Gravity.START -> mTitle!!.setCompoundDrawables(drawable, null, null, null)
            Gravity.TOP -> mTitle!!.setCompoundDrawables(null, drawable, null, null)
            Gravity.END -> mTitle!!.setCompoundDrawables(null, null, drawable, null)
            Gravity.BOTTOM -> mTitle!!.setCompoundDrawables(null, null, null, drawable)
        }
        refreshDrawablePadding()
    }

    private fun refreshDrawablePadding() {
        val iconResid = if (mChecked) mTabIcon!!.getSelectedIcon() else mTabIcon!!.getNormalIcon()
        if (iconResid != 0) {
            if (!TextUtils.isEmpty(mTabTitle!!.getContent()) && mTitle!!.compoundDrawablePadding != mTabIcon!!.getMargin()) {
                mTitle!!.compoundDrawablePadding = mTabIcon!!.getMargin()
            } else if (TextUtils.isEmpty(mTabTitle!!.getContent())) {
                mTitle!!.compoundDrawablePadding = 0
            }
        } else {
            mTitle!!.compoundDrawablePadding = 0
        }
    }

    override fun setBadge(badge: TabBadge?): QTabView? {
        if (badge != null) {
            mTabBadge = badge
        }
        initBadge()
        return this
    }

    override fun setIcon(icon: TabIcon?): QTabView? {
        if (icon != null) {
            mTabIcon = icon
        }
        initIconView()
        return this
    }

    override fun setTitle(title: TabTitle?): QTabView? {
        if (title != null) {
            mTabTitle = title
        }
        initTitleView()
        return this
    }

    /**
     * @param resId The Drawable res to use as the background, if less than 0 will to remove the
     * background
     */
    override fun setBackground(resId: Int): QTabView? {
        if (resId == 0) {
            setDefaultBackground()
        } else if (resId <= 0) {
            background = null
        } else {
            super.setBackgroundResource(resId)
        }
        return this
    }

    override fun getBadge(): TabBadge? {
        return mTabBadge
    }

    override fun getIcon(): TabIcon? {
        return mTabIcon
    }

    override fun getTitle(): TabTitle? {
        return mTabTitle
    }

    @Deprecated("")
    override fun getIconView(): ImageView? {
        return null
    }

    override fun getTitleView(): TextView? {
        return mTitle
    }

    override fun getBadgeView(): Badge? {
        return mBadgeView
    }

    override fun setBackground(background: Drawable?) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            super.setBackground(background)
        } else {
            super.setBackgroundDrawable(background)
        }
    }

    override fun setBackgroundResource(resid: Int) {
        setBackground(resid)
    }

    private fun setDefaultBackground() {
        if (background !== mDefaultBackground) {
            background = mDefaultBackground
        }
    }

    override fun setChecked(checked: Boolean) {
        mChecked = checked
        isSelected = checked
        refreshDrawableState()
        mTitle!!.setTextColor(if (checked) mTabTitle!!.getColorSelected() else mTabTitle!!.getColorNormal())
        initIconView()
    }

    override fun isChecked(): Boolean {
        return mChecked
    }

    override fun toggle() {
        isChecked = !mChecked
    }

}