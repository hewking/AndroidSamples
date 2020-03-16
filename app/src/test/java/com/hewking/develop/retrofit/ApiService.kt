package com.hewking.develop.retrofit

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.http.GET

interface ApiService {

    companion object{
        fun getApi():ApiService{
            val retrofit = Retrofit.Builder()
                .baseUrl("http://t.weather.sojson.com/api/")
                .build()
            return retrofit.create(ApiService::class.java)
        }
    }

    @GET("http://t.weather.sojson.com/api/weather/city/101030100")
    fun getWeather():Call<ResponseBody>

}