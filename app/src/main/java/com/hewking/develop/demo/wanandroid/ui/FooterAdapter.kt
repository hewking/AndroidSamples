package com.hewking.develop.demo.wanandroid.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.hewking.develop.R
import com.hewking.develop.databinding.FooterItemBinding

class FooterAdapter(val retry: () -> Unit) : LoadStateAdapter<FooterAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val binding = FooterItemBinding.bind(itemView)

        companion object {

            fun create(parent: ViewGroup): ViewHolder{
                val view = LayoutInflater.from(parent.context).inflate(R.layout.footer_item, parent, false)
                return ViewHolder(view)
            }

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): ViewHolder {
        val holder = ViewHolder.create(parent)
        holder.binding.retryButton.setOnClickListener {
            retry()
        }
        return holder
    }

    override fun onBindViewHolder(holder: ViewHolder, loadState: LoadState) {
        holder.binding.progressBar.isVisible = loadState is LoadState.Loading
        holder.binding.retryButton.isVisible = loadState is LoadState.Error
    }

}