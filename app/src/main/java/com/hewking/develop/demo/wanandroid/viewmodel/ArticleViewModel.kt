package com.hewking.develop.demo.wanandroid.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.hewking.develop.demo.wanandroid.repository.WanAndroidRepository

class ArticleViewModel(repo: WanAndroidRepository): ViewModel() {

    val flow = repo.postsOfArticle(20).cachedIn(viewModelScope)

}