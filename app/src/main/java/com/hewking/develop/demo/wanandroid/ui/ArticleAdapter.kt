package com.hewking.develop.demo.wanandroid.ui

import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import com.hewking.develop.demo.wanandroid.entity.Article

class ArticleAdapter() : PagingDataAdapter<Article, ArticleViewHolder>(ARTICLE_COMPARATOR) {

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        return ArticleViewHolder.create(parent)
    }

    companion object {

        val ARTICLE_COMPARATOR = object : DiffUtil.ItemCallback<Article>(){
            override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
                return oldItem == newItem
            }

        }

    }

}