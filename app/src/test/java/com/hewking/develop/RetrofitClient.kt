package com.hewking.develop

import okhttp3.OkHttpClient
import retrofit2.Retrofit

object RetrofitClient {

    var retrofit: Retrofit = Retrofit.Builder()
        .baseUrl("http://t.weather.sojson.com/api/")
        .client(OkHttpClient())
        .build()

}