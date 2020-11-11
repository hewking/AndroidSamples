package com.hewking.develop.retrofit

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import okhttp3.ResponseBody
import org.junit.Test
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.system.measureTimeMillis

class RetrofitApiUnitTest {

    @Test
    fun testApi() {
        println("request start")
        ApiService.getApi().getWeather().enqueue(object : Callback<ResponseBody> {
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                t.printStackTrace()
            }

            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
//                println(buildString {
//                    append("response:")
//                    append(response.body()?.string())
//                })
            }

        })

        val resp = ApiService.getApi().getWeather().execute().body()?.string()
        println("response:${resp}")
    }

    suspend fun delayOne(): Int{
        delay(1000)
        return 1
    }

    @Test
    fun testApi2() {
        GlobalScope.launch {
            val consumerTime = measureTimeMillis {
                var sum = ""
                val asyncList = IntArray(20).also {
                    it.fill(10)
                }.map {
                    val data1 = ApiService.getApi().getWeather2()
//                    async {  delayOne()}
//                    println("data:$data1")
                    data1
                }

                asyncList.forEach {
                    sum += it.await().toString()
                }
                println("sum: $sum")
            }

            println("consumerTime:$consumerTime")

        }
        println("----------------")
        Thread.sleep(20000)
    }

}