package com.hewking.develop.demo.wanandroid.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.hewking.develop.demo.wanandroid.repository.WanAndroidRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.flow.SharingStarted.Companion.WhileSubscribed

class ArticleViewModel(repo: WanAndroidRepository): ViewModel() {

    val flow = repo.postsOfArticle(20).cachedIn(viewModelScope)

    val result = repo.friendArticle().stateIn(
        scope = viewModelScope,
        started = WhileSubscribed(5000),
        initialValue = null
    )

}