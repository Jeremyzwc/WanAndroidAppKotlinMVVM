package com.qisan.wanandroid.adapter

import android.app.ActivityOptions
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.qisan.baselib.adapter.BaseRvAdapter
import com.qisan.wanandroid.R
import com.qisan.wanandroid.databinding.ItemNavigationListBinding
import com.qisan.wanandroid.entity.Article
import com.qisan.wanandroid.entity.NavigationBean
import com.zhy.view.flowlayout.FlowLayout
import com.zhy.view.flowlayout.TagAdapter
import com.zhy.view.flowlayout.TagFlowLayout
import com.qisan.baselib.adapter.ViewBindingHolder
import com.qisan.baselib.utils.CommonUtil
import com.qisan.baselib.utils.DisplayManager

/**
 * Created by QiSan 2022/6/15
 * package com.qisan.wanandroid.adapter
 */
class NavigationAdapter: BaseRvAdapter<NavigationBean, ItemNavigationListBinding>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewBindingHolder<ItemNavigationListBinding> {
        val inflate = ItemNavigationListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewBindingHolder(inflate)
    }

    override fun bindRvData(holder: ViewBindingHolder<ItemNavigationListBinding>, bindingAdapterPosition: Int, item: NavigationBean) {
        holder.binding.itemNavigationTv.text = item.name
        val flowLayout: TagFlowLayout = holder.binding.itemNavigationFlowLayout
        val articles: List<Article> = item.articles
        flowLayout.run {
            adapter = object : TagAdapter<Article>(articles) {
                override fun getView(parent: FlowLayout?, position: Int, article: Article?): View? {

                    val tv: TextView = LayoutInflater.from(parent?.context).inflate(
                        R.layout.flow_layout_tv,
                        flowLayout, false
                    ) as TextView

                    article ?: return null

                    val padding: Int = DisplayManager.dip2px(10F)
                    tv.setPadding(padding, padding, padding, padding)
                    tv.text = article.title
                    tv.setTextColor(CommonUtil.randomColor())

                    setOnTagClickListener { view, position, _ ->
                        val options: ActivityOptions = ActivityOptions.makeScaleUpAnimation(
                            view,
                            view.width / 2,
                            view.height / 2,
                            0,
                            0
                        )
                        val data: Article = articles[position]
//                        ContentActivity.start(context, data.id, data.title, data.link, options.toBundle())
                        true
                    }
                    return tv
                }
            }
        }
    }
}