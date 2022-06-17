package com.qisan.wanandroid.widget.verticaltablayout.widget

import android.graphics.Color
import android.graphics.drawable.Drawable
import android.view.Gravity
import android.view.View
import q.rorbin.badgeview.Badge.OnDragStateChangedListener

/**
 * Created by QiSan 2022/6/18
 * package com.qisan.wanandroid.widget.verticaltablayout.widget
 */
interface ITabView {

    fun setBadge(badge: TabBadge?): ITabView?

    fun setIcon(icon: TabIcon?): ITabView?

    fun setTitle(title: TabTitle?): ITabView?

    fun setBackground(resid: Int): ITabView?

    fun getBadge(): TabBadge?

    fun getIcon(): TabIcon?

    fun getTitle(): TabTitle?

    fun getTabView(): View?

    class TabIcon constructor(private val mBuilder: Builder) {

        fun getSelectedIcon(): Int {
            return mBuilder.mSelectedIcon
        }

        fun getNormalIcon(): Int {
            return mBuilder.mNormalIcon
        }

        fun getIconGravity(): Int {
            return mBuilder.mIconGravity
        }

        fun getIconWidth(): Int {
            return mBuilder.mIconWidth
        }

        fun getIconHeight(): Int {
            return mBuilder.mIconHeight
        }

        fun getMargin(): Int {
            return mBuilder.mMargin
        }

         class Builder {

            var mSelectedIcon = 0
            var mNormalIcon = 0
            var mIconGravity = Gravity.START
            var mIconWidth = -1
            var mIconHeight = -1
            var mMargin = 0

            fun setIcon(selectIconResId: Int, normalIconResId: Int): Builder {
                mSelectedIcon = selectIconResId
                mNormalIcon = normalIconResId
                return this
            }

            fun setIconSize(width: Int, height: Int): Builder {
                mIconWidth = width
                mIconHeight = height
                return this
            }

            fun setIconGravity(gravity: Int): Builder {
                check(
                    !(gravity != Gravity.START && (gravity != Gravity.END
                            ) and (gravity != Gravity.TOP) and (gravity != Gravity.BOTTOM))
                ) {
                    "iconGravity only support Gravity.START " +
                            "or Gravity.END or Gravity.TOP or Gravity.BOTTOM"
                }
                mIconGravity = gravity
                return this
            }

            fun setIconMargin(margin: Int): Builder {
                mMargin = margin
                return this
            }

            fun build(): TabIcon {
                return TabIcon(this)
            }
        }
    }

    class TabTitle private constructor(private val mBuilder: Builder) {

        fun getColorSelected(): Int {
            return mBuilder.mColorSelected
        }

        fun getColorNormal(): Int {
            return mBuilder.mColorNormal
        }

        fun getTitleTextSize(): Int {
            return mBuilder.mTitleTextSize
        }

        fun getContent(): String {
            return mBuilder.mContent
        }

        class Builder {

            var mColorSelected = -0xbf7f
            var mColorNormal = -0x8a8a8b
            var mTitleTextSize = 16
            var mContent: String = ""


            fun setTextColor(colorSelected: Int, colorNormal: Int): Builder? {
                mColorSelected = colorSelected
                mColorNormal = colorNormal
                return this
            }

            fun setTextSize(sizeSp: Int): Builder? {
                mTitleTextSize = sizeSp
                return this
            }

            fun setContent(content: String): Builder? {
                mContent = content
                return this
            }

            fun build(): TabTitle {
                return TabTitle(this)
            }

        }
    }

    class TabBadge private constructor(private val mBuilder: Builder) {
        fun getBackgroundColor(): Int {
            return mBuilder.colorBackground
        }

        fun getBadgeTextColor(): Int {
            return mBuilder.colorBadgeText
        }

        fun getBadgeTextSize(): Float {
            return mBuilder.badgeTextSize
        }

        fun getBadgePadding(): Float {
            return mBuilder.badgePadding
        }

        fun getBadgeNumber(): Int {
            return mBuilder.badgeNumber
        }

        fun getBadgeText(): String? {
            return mBuilder.badgeText
        }

        fun getBadgeGravity(): Int {
            return mBuilder.badgeGravity
        }

        fun getGravityOffsetX(): Int {
            return mBuilder.gravityOffsetX
        }

        fun getGravityOffsetY(): Int {
            return mBuilder.gravityOffsetY
        }

        fun isExactMode(): Boolean {
            return mBuilder.exactMode
        }

        fun isShowShadow(): Boolean {
            return mBuilder.showShadow
        }

        fun getDrawableBackground(): Drawable? {
            return mBuilder.drawableBackground
        }

        fun getStrokeColor(): Int {
            return mBuilder.colorStroke
        }

        fun isDrawableBackgroundClip(): Boolean {
            return mBuilder.drawableBackgroundClip
        }

        fun getStrokeWidth(): Float {
            return mBuilder.strokeWidth
        }

        fun getOnDragStateChangedListener(): OnDragStateChangedListener? {
            return mBuilder.onDragStateChangedListener
        }

        class Builder {
            var colorBackground: Int
            var colorBadgeText: Int
            var colorStroke: Int
            var drawableBackground: Drawable?
            var drawableBackgroundClip: Boolean

            var strokeWidth: Float
            var badgeTextSize: Float
            var badgePadding: Float
            var badgeNumber: Int
            var badgeText: String?
            var badgeGravity: Int
            var gravityOffsetX: Int
            var gravityOffsetY: Int
            var exactMode: Boolean

            var showShadow: Boolean

            var onDragStateChangedListener: OnDragStateChangedListener? = null

            fun build(): TabBadge {
                return TabBadge(this)
            }

            fun stroke(color: Int, strokeWidth: Int): Builder {
                this.colorStroke = color
                this.strokeWidth = strokeWidth.toFloat()
                return this
            }

            fun setDrawableBackground(drawableBackground: Drawable?, clip: Boolean): Builder {
                this.drawableBackground = drawableBackground
                this.drawableBackgroundClip = clip
                return this
            }

            fun setShowShadow(showShadow: Boolean): Builder {
                this.showShadow = showShadow
                return this
            }

            fun setOnDragStateChangedListener(dragStateChangedListener: OnDragStateChangedListener?): Builder {
                onDragStateChangedListener = dragStateChangedListener
                return this
            }

            fun setExactMode(exactMode: Boolean): Builder {
                this.exactMode = exactMode
                return this
            }

            fun setBackgroundColor(colorBackground: Int): Builder {
                this.colorBackground = colorBackground
                return this
            }

            fun setBadgePadding(dpValue: Float): Builder {
                badgePadding = dpValue
                return this
            }

            fun setBadgeNumber(badgeNumber: Int): Builder {
                this.badgeNumber = badgeNumber
                badgeText = null
                return this
            }

            fun setBadgeGravity(badgeGravity: Int): Builder {
                this.badgeGravity = badgeGravity
                return this
            }

            fun setBadgeTextColor(colorBadgeText: Int): Builder {
                this.colorBadgeText = colorBadgeText
                return this
            }

            fun setBadgeTextSize(badgeTextSize: Float): Builder {
                this.badgeTextSize = badgeTextSize
                return this
            }

            fun setBadgeText(badgeText: String?): Builder {
                this.badgeText = badgeText
                badgeNumber = 0
                return this
            }

            fun setGravityOffset(offsetX: Int, offsetY: Int): Builder {
                gravityOffsetX = offsetX
                gravityOffsetY = offsetY
                return this
            }

            init {
                colorBackground = -0x17b1c0
                colorBadgeText = -0x1
                colorStroke = Color.TRANSPARENT
                drawableBackground = null
                drawableBackgroundClip = false
                strokeWidth = 0f
                badgeTextSize = 11f
                badgePadding = 5f
                badgeNumber = 0
                badgeText = null
                badgeGravity = Gravity.END or Gravity.TOP
                gravityOffsetX = 1
                gravityOffsetY = 1
                exactMode = false
                showShadow = true
            }
        }
    }

}