package com.hewking.develop.demo.wanandroid.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.hewking.develop.demo.wanandroid.api.WanAndroidApi
import com.hewking.develop.demo.wanandroid.entity.Article
import kotlinx.coroutines.flow.Flow

class DefaultWanAndroidRepository(private val api: WanAndroidApi) : WanAndroidRepository {

    override fun postsOfArticle(
        pageSize: Int
    ): Flow<PagingData<Article>> = Pager(
        config = PagingConfig(
            pageSize = pageSize
        )
    ) {
        ArticlePagingSource(api)
    }.flow
}