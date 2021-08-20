package com.hewking.develop.demo.wanandroid.repository

import androidx.paging.PagingData
import com.hewking.develop.demo.wanandroid.entity.Article
import com.hewking.develop.demo.wanandroid.entity.FriendArticle
import kotlinx.coroutines.flow.Flow

interface WanAndroidRepository {

    fun postsOfArticle(pageSize: Int): Flow<PagingData<Article>>

    fun friendArticle(): Flow<List<FriendArticle>>

}