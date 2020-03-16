package com.hewking.develop.retrofit

import okhttp3.ResponseBody
import org.junit.Test
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RetrofitApiUnitTest {

    @Test
    fun testApi(){
        println("request start")
//        ApiService.getApi().getWeather().enqueue(object: Callback<ResponseBody>{
//            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
//                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
//            }
//
//            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
//                println(buildString {
//                    append("response:")
//                    append(response.body()?.string())
//                })
//            }
//
//        })

        val resp = ApiService.getApi().getWeather().execute().body()?.string()
        println("response:${resp}")
    }

}