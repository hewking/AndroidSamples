package com.hewking.develop.demo.wanandroid.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hewking.develop.R
import com.hewking.develop.databinding.ItemMessageBinding
import com.hewking.develop.demo.wanandroid.entity.Article

class ArticleViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

    private val _binding = ItemMessageBinding.bind(itemView)

    fun bind(item: Article?) {
        _binding.tvUsername.text = item?.author
        _binding.tvMessage.text = item?.desc
    }

    companion object {

        fun create(parent: ViewGroup): ArticleViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.item_message, parent, false)
            return ArticleViewHolder(view)
        }

    }

}