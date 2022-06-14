package com.qisan.wanandroid.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import cn.bingoogolapple.bgabanner.BGABanner
import com.qisan.wanandroid.databinding.ItemArticleBinding
import com.qisan.wanandroid.databinding.ItemHomeBannerBinding
import com.qisan.wanandroid.entity.Banner
import com.qisan.wanandroid.utils.GlideUtils

/**
 * Created by qisan 2022/5/31
 * com.qisan.wanandroid.adapter
 */
class HomeBannerAdapter(var context: Context?) : BaseRvAdapter<MutableList<Banner>?, ItemHomeBannerBinding>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewBindingHolder<ItemHomeBannerBinding> {
        val inflate = ItemHomeBannerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewBindingHolder(inflate)
    }

    override fun bindRvData(holder: ViewBindingHolder<ItemHomeBannerBinding>, bindingAdapterPosition: Int, item: MutableList<Banner>?) {

        val bannerFeedList = ArrayList<String>()
        val bannerTitleList = ArrayList<String>()
        item?.forEach { banner ->
            bannerFeedList.add(banner.imagePath)
            bannerTitleList.add(banner.title)
        }

        val bannerDelegate = BGABanner.Delegate<ImageView, String> { banner, imageView, model, position ->
            if (item?.isNotEmpty() == true) {
                val data = item[position]
//            ContentActivity.start(activity, data.id, data.title, data.url)
            }
        }

        holder.binding.banner.run {
            setDelegate(bannerDelegate)
        }

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

