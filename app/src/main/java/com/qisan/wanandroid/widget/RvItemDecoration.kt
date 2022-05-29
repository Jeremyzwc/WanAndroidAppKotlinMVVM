package com.qisan.wanandroid.widget

import android.content.Context
import android.graphics.Canvas
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.view.View
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager

/**
 * Created by QiSan 2022/5/30
 * package com.qisan.wanandroid.widget
 */
class RvItemDecoration(context: Context): RecyclerView.ItemDecoration() {

    private var mDivider: Drawable? = null
    private val mSectionOffsetV: Int = 0
    private val mSectionOffsetH: Int = 0
    private var mDrawOver = true
    private var attrs: IntArray = intArrayOf(android.R.attr.listDivider)

    init {
        val a = context.obtainStyledAttributes(attrs)
        mDivider = a.getDrawable(0)
        a.recycle()
    }

    override fun onDrawOver(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        super.onDrawOver(c, parent, state)
        if (mDivider != null && mDrawOver) {
            draw(c, parent)
        }
    }

    override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        super.onDraw(c, parent, state)
        if (mDivider != null && mDrawOver) {
            draw(c, parent)
        }
    }

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        super.getItemOffsets(outRect, view, parent, state)

        if (getOrientation(parent.layoutManager) == RecyclerView.VERTICAL) {
            outRect.set(mSectionOffsetH, 0, mSectionOffsetH, mSectionOffsetV)
        } else {
            outRect.set(0, 0, mSectionOffsetV, 0)
        }
    }

    private fun draw(c: Canvas, parent: RecyclerView) {
        val left = parent.paddingLeft
        val right = parent.width - parent.paddingRight

        val childCount = parent.childCount
        for (i in 0 until childCount) {
            val child = parent.getChildAt(i)

            val params = child.layoutParams as RecyclerView.LayoutParams

            val top = child.bottom + params.bottomMargin + Math.round(ViewCompat.getTranslationY(child))
            val bottom = top + if (mDivider!!.intrinsicHeight <= 0) 1 else mDivider!!.intrinsicHeight

            mDivider?.let {
                it.setBounds(left, top, right, bottom)
                it.draw(c)
            }
        }
    }

    private fun getOrientation(layoutManager: RecyclerView.LayoutManager?): Int {
        if (layoutManager is LinearLayoutManager) {
            return layoutManager.orientation
        } else if (layoutManager is StaggeredGridLayoutManager) {
            return layoutManager.orientation
        }
        return androidx.recyclerview.widget.OrientationHelper.HORIZONTAL
    }

}