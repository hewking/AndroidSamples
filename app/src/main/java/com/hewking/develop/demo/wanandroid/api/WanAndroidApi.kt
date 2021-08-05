package com.hewking.develop.demo.wanandroid.api

import android.util.Log
import com.hewking.develop.demo.wanandroid.entity.BaseResponse
import com.hewking.develop.demo.wanandroid.entity.FriendArticle
import com.hewking.develop.demo.wanandroid.entity.WanHomeResponse
import okhttp3.HttpUrl.Companion.toHttpUrl
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

interface WanAndroidApi {

    @GET("article/list/{page}/json")
    suspend fun getArticle(
        @Path("page") page: Int,
    ): BaseResponse<WanHomeResponse>

    @GET("https://www.wanandroid.com/friend/json")
    suspend fun getFriendArticle(): BaseResponse<List<FriendArticle>>


    companion object {
        private const val BASE_URL = "https://www.wanandroid.com/"
        fun create(): WanAndroidApi {
            val logger = HttpLoggingInterceptor(HttpLoggingInterceptor.Logger { Log.d("API", it) })
            logger.level = HttpLoggingInterceptor.Level.BODY

            val client = OkHttpClient.Builder()
                .addInterceptor(logger)
                .build()
            return Retrofit.Builder()
                .baseUrl(BASE_URL.toHttpUrl())
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(WanAndroidApi::class.java)
        }
    }

}