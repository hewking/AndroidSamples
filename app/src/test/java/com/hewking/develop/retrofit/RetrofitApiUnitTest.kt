package com.hewking.develop.retrofit

import com.hewking.develop.util.ioAsync
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import okhttp3.ResponseBody
import org.junit.Test
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.concurrent.CountDownLatch
import kotlin.concurrent.thread
import kotlin.system.measureTimeMillis

class RetrofitApiUnitTest {

    @Test
    fun testApi() {
        println("request start")
//        ApiService.getApi().getWeather().enqueue(object : Callback<ResponseBody> {
//            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
//                t.printStackTrace()
//            }
//
//            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
////                println(buildString {
////                    append("response:")
////                    append(response.body()?.string())
////                })
//            }
//
//        })

        val resp = ApiService.getApi().getWeather().execute().body()?.string()
        println("response:${resp}")
    }

    suspend fun delayOne(): Int {
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

    @Test
    fun testApi3() {
        GlobalScope.launch {
            val consumerTime = measureTimeMillis {
                var sum = ""
                val asyncList = IntArray(20).also {
                    it.fill(10)
                }.map {
                    val data1 = ioAsync { ApiService.getApi().getWeather3() }
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


    @Test
    fun testApi4() {
        GlobalScope.launch {
            val consumerTime = measureTimeMillis {
                var sum = ""
                val cl = CountDownLatch(20)
                val asyncList = IntArray(20)
                    .forEach {
                        thread {
                            launch {
                                val result = ApiService.getApi().getWeather3()
                                sum += result
                                cl.countDown()
                            }
                        }
                    }

                cl.await()

                println("sum: $sum")
            }

            println("consumerTime:$consumerTime")

        }
        println("----------------")
        Thread.sleep(20000)
    }

}