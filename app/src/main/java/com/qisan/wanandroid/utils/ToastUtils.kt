package com.qisan.wanandroid.utils

import android.content.Context
import android.widget.Toast
import com.qisan.wanandroid.WanApplication
import java.util.*

/**
 * Created by qisan 2022/5/27
 * com.qisan.wanandroid.utils
 */
class ToastUtils {

    companion object {


        fun show(text: CharSequence) {
            show(WanApplication.context, text, 0)
        }

        fun show(resId: Int) {
            show(WanApplication.context, resId, 0)
        }

        fun show(context: Context, resId: Int) {
            show(context, context.resources.getText(resId), 0)
        }

        fun show(context: Context, resId: Int, duration: Int) {
            show(context, context.resources.getText(resId), duration)
        }

        fun show(context: Context, text: CharSequence) {
            show(context, text, 0)
        }

        fun showCenter(context: Context, text: CharSequence?) {
            val toast: Toast = Toast.makeText(context.applicationContext, text, Toast.LENGTH_SHORT)
            toast.setGravity(17, 0, 0)
            toast.show()
        }

        fun show(context: Context, text: CharSequence, duration: Int) {
            val text: String? = IPUtils.getDealMsg(text.toString())
            Toast.makeText(context.applicationContext, text, duration).show()
        }

        fun show(context: Context?, text: String?, vararg args: Any?) {
            show(context, String.format(text!!, *args), 0)
        }

        fun show(context: Context?, text: String?, duration: Int, vararg args: Any?) {
            show(context, String.format(text!!, *args), duration)
        }

        fun showAtLocation(context: Context, text: String?, duration: Int, xOffset: Int, yOffset: Int) {
            val toast: Toast = Toast.makeText(context.applicationContext, text, duration)
            toast.setGravity(51, xOffset, yOffset)
            toast.show()
        }

        fun showWithTime(context: Context?, text: String?, time: Int) {
            val toast: Toast = Toast.makeText(context, text, Toast.LENGTH_LONG)
            showWithTime(toast, time)
        }

        fun showWithTime(context: Context, resId: Int, time: Int) {
            val toast: Toast = Toast.makeText(context, context.getString(resId), Toast.LENGTH_LONG)
            showWithTime(toast, time)
        }

        private fun showWithTime(toast: Toast, time: Int) {
            val timer = Timer()
            timer.schedule(object : TimerTask() {
                override fun run() {
                    toast.show()
                }
            }, 0L, 3000L)
            Timer().schedule(object : TimerTask() {
                override fun run() {
                    toast.cancel()
                    timer.cancel()
                }
            }, time.toLong())
        }
    }
}