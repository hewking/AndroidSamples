package com.hewking.develop.demo.wanandroid.repository

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.hewking.develop.demo.wanandroid.api.WanAndroidApi
import com.hewking.develop.demo.wanandroid.entity.Article
import okio.IOException
import retrofit2.HttpException

class ArticlePagingSource(val api: WanAndroidApi) : PagingSource<Int, Article>() {

    override fun getRefreshKey(state: PagingState<Int, Article>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Article> {
        return try {
            val nextPageNumber = params.key ?: 1
            val response = api.getArticle(nextPageNumber)
            val items = response.datas
            LoadResult.Page(
                data = items?: emptyList(),
                prevKey = null,
                nextKey = null,
            )
        } catch (ex: IOException) {
            LoadResult.Error(ex)
        } catch (ex: HttpException) {
            LoadResult.Error(ex)
        }catch (ex: Exception) {
            LoadResult.Error(ex)
        }
    }
}