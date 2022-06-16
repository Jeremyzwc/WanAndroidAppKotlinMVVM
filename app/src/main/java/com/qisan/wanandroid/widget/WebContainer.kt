package com.qisan.wanandroid.widget

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.util.AttributeSet
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.content.ContextCompat
import com.qisan.wanandroid.R
import com.qisan.wanandroid.utils.ColorUtil
import com.qisan.wanandroid.utils.SettingUtil

/**
 * Created by qisan 2022/6/16
 * com.qisan.wanandroid.widget
 */
class WebContainer: CoordinatorLayout {

    private var mDarkTheme: Boolean = false

    private var mMaskColor = Color.TRANSPARENT

    init {
        mDarkTheme = SettingUtil.getIsNightMode()
        if (mDarkTheme) {
            mMaskColor = ColorUtil.alphaColor(ContextCompat.getColor(context, R.color.mask_color), 0.6f)
        }
    }

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    override fun dispatchDraw(canvas: Canvas) {
        super.dispatchDraw(canvas)
        if (mDarkTheme) {
            canvas.drawColor(mMaskColor)
        }
    }
}