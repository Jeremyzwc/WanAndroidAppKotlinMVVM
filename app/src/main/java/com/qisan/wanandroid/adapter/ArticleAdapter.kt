package com.qisan.wanandroid.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.qisan.baselib.adapter.ViewBindingHolder
import com.qisan.baselib.utils.ToastUtils
import com.qisan.wanandroid.R
import com.qisan.wanandroid.databinding.ItemArticleBinding
import com.qisan.wanandroid.entity.Article
import com.qisan.wanandroid.global.WanUser
import com.qisan.baselib.listener.OnMultiClickListener
import com.qisan.baselib.utils.GlideUtils
import com.qisan.wanandroid.ui.activity.LoginActivity


/**
 * Created by QiSan 2022/5/29
 * package com.qisan.wanandroid.adapter
 */
class ArticleAdapter(var context: Context) : BasePagingDataAdapter<Article, ItemArticleBinding>(
    itemCallback(
        areItemsTheSame = { oldItem, newItem ->
            oldItem.id == newItem.id
        },
        areContentsTheSame = { oldItem, newItem ->
            oldItem.id == newItem.id && oldItem.title == newItem.title
        }
    )
) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewBindingHolder<ItemArticleBinding> {
        val inflate = ItemArticleBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewBindingHolder(inflate)
    }

    override fun onBindViewHolder(holder: ViewBindingHolder<ItemArticleBinding>, position: Int, binding: ItemArticleBinding, item: Article) {
        holder.binding.tvArticleTitle.text = item.title
        holder.binding.tvArticleAuthor.text = item.author.ifEmpty { item.shareUser }
        holder.binding.tvArticleDate.text = item.niceDate
        holder.binding.ivLike.setImageResource(if (item.collect) R.drawable.ic_like else R.drawable.ic_like_not)
        val chapterName = when {
            item.superChapterName.isNotEmpty() and item.chapterName.isNotEmpty() -> {
                "${item.superChapterName} / ${item.chapterName}"
            }
            item.superChapterName.isNotEmpty() -> {
                item.superChapterName
            }
            item.chapterName.isNotEmpty() -> {
                item.chapterName
            }
            else -> ""
        }

        holder.binding.tvArticleChapterName.text = chapterName
        if (item.envelopePic.isNotEmpty()) {
            holder.binding.ivArticleThumbnail.visibility = View.VISIBLE
            GlideUtils.load(context, item.envelopePic, holder.binding.ivArticleThumbnail)
        } else {
            holder.binding.ivArticleThumbnail.visibility = View.GONE
        }

        if (item.fresh) {
            holder.binding.tvArticleFresh.visibility = View.VISIBLE
        } else {
            holder.binding.tvArticleFresh.visibility = View.GONE
        }

        if (item.top == "1") {
            holder.binding.tvArticleTop.visibility = View.VISIBLE
        } else {
            holder.binding.tvArticleTop.visibility = View.GONE
        }

        if (item.tags.size > 0) {
            holder.binding.tvArticleTag.visibility = View.VISIBLE
            holder.binding.tvArticleTag.text = item.tags[0].name
        } else {
            holder.binding.tvArticleTag.visibility = View.GONE
        }

        holder.binding.ivLike.setOnClickListener(object : OnMultiClickListener() {
            override fun onMultiClick(view: View?) {
                if(WanUser.isLogin()){
                    val collect = item.collect
                    item.collect = !collect
                    notifyItemChanged(position, item)
                    if(!collect){
                        viewModel?.addCollectArticle(item.id)
                    }else{
                        viewModel?.cancelCollectArticle(item.id)
                    }
                }else{
                    LoginActivity.startActivity(context)
                    ToastUtils.show(context.resources.getString(R.string.login_tint))
                }
            }
        })
    }

}