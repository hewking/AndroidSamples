package com.hewking.develop.retrofit

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import kotlinx.coroutines.Deferred
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface ApiService {

    companion object{
        fun getApi():ApiService{
            val retrofit = Retrofit.Builder()
                .baseUrl("http://t.weather.sojson.com/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .build()
            return retrofit.create(ApiService::class.java)
        }
    }

    @GET("https://gank.io/api/v2/data/category/Girl/type/Girl/page/1/count/10")
    fun getWeather():Call<ResponseBody>

    @GET("https://gank.io/api/v2/categories/GanHuo")
    fun getWeather2(): Deferred<Any>

    @GET("https://gank.io/api/v2/categories/GanHuo")
    suspend fun getWeather3(): Any

}