package com.qisan.wanandroid.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.paging.LoadState
import cn.bingoogolapple.bgabanner.BGABanner
import com.qisan.wanandroid.base.ViewBindingHolder
import com.qisan.wanandroid.databinding.ItemHomeBannerBinding
import com.qisan.wanandroid.utils.GlideUtils

/**
 * Created by qisan 2022/5/31
 * com.qisan.wanandroid.adapter
 */
class HomeBannerAdapter(private val context: Context?,private var bannerFeedList: List<String>,private var bannerTitleList: List<String>): BaseLoadStateAdapter<ItemHomeBannerBinding>() {

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): ViewBindingHolder<ItemHomeBannerBinding> {
        val inflate = ItemHomeBannerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewBindingHolder(inflate)
    }

    override fun onBindViewHolder(holder: ViewBindingHolder<ItemHomeBannerBinding>, loadState: LoadState) {
        val bannerAdapter: BGABanner.Adapter<ImageView, String> by lazy {
            BGABanner.Adapter<ImageView, String> { _, imageView, feedImageUrl, _ ->
                GlideUtils.load(context, feedImageUrl, imageView)
            }
        }

        holder.binding.banner.run {
            setAutoPlayAble(bannerFeedList.size > 1)
            setData(bannerFeedList, bannerTitleList)
            setAdapter(bannerAdapter)
        }
    }
}

