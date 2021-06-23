package com.hewking.develop.demo.wanandroid.repository

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.hewking.develop.demo.wanandroid.api.WanAndroidApi
import com.hewking.develop.demo.wanandroid.entity.Article
import okio.IOException
import retrofit2.HttpException

class ArticlePagingSource(val api: WanAndroidApi) : PagingSource<Int, Article>() {

//    override fun getRefreshKey(state: PagingState<Int, Article>): Int? {
//        return state.anchorPosition?.let { anchorPosition ->
//            val anchorPage = state.closestPageToPosition(anchorPosition)
//            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
//        }
//    }

    override fun getRefreshKey(state: PagingState<Int, Article>): Int? = null

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Article> {
        return try {
            val page = params.key ?: 1 // set page 1 as default
            val pageSize = params.loadSize
            val repoResponse = api.getArticle(page)
            val repoItems = repoResponse.data.datas
            val prevKey = if (page > 1) page - 1 else null
            val nextKey = if (!repoItems.isNullOrEmpty()) page + 1 else null

            LoadResult.Page(repoItems?: emptyList(), prevKey, nextKey)

        } catch (ex: IOException) {
            LoadResult.Error(ex)
        } catch (ex: HttpException) {
            LoadResult.Error(ex)
        }catch (ex: Exception) {
            LoadResult.Error(ex)
        }
    }
}