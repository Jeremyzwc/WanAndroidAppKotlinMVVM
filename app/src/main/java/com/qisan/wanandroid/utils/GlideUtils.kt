package com.qisan.wanandroid.utils

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.qisan.wanandroid.R

/**
 * Created by QiSan 2022/5/30
 * package com.qisan.wanandroid.utils
 */
object GlideUtils {

    fun load(context: Context?, url: String?, iv: ImageView?) {

        iv?.apply {
            Glide.with(context!!).clear(iv)
            val options = RequestOptions()
                .diskCacheStrategy(DiskCacheStrategy.DATA)
                .placeholder(R.drawable.bg_placeholder)
            Glide.with(context)
                .load(url)
                .transition(DrawableTransitionOptions().crossFade())
                .apply(options)
                .into(iv)
        }
    }

}