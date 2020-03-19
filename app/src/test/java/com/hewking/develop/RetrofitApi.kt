package com.hewking.develop

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.GET

interface RetrofitApi {

    companion object {

        fun getApi(): RetrofitApi {
            return RetrofitClient.retrofit.create(RetrofitApi::class.java)
        }
    }

    @GET("http://t.weather.sojson.com/api/weather/city/101030100")
    fun getWeather(): Call<ResponseBody>

}