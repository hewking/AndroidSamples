package com.hewking.develop

import okhttp3.*
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before
import java.io.IOException

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {

    private val okHttpClient: OkHttpClient by lazy {
        OkHttpClient.Builder()
            .build()
    }

    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    @Before
    fun initOkHttp(){
        okHttpClient
    }

    @Test
    fun testCall(){
        val request = Request.Builder()
            .url("http://t.weather.sojson.com/api/weather/city/101030100")
            .build()
        println("testCall,start")
        okHttpClient.newCall(request).enqueue(object : Callback{
            override fun onFailure(call: Call, e: IOException) {
                println("testCall,onFailure")

            }

            override fun onResponse(call: Call, response: Response) {
                val json = response.body?.string()
                println("testCall,onSuccess：json:$json")
            }

        })

    }

    @Test
    fun testRetrofit(){
        val call = RetrofitApi.getApi().getWeather()
        println(Thread.currentThread().name)
        call.enqueue(object : retrofit2.Callback<ResponseBody>{
            override fun onFailure(call: retrofit2.Call<ResponseBody>, t: Throwable) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onResponse(
                call: retrofit2.Call<ResponseBody>,
                response: retrofit2.Response<ResponseBody>
            ) {
                println(Thread.currentThread().name)
                println("testRetrofit，resp:${response.body()?.string()}")
            }

        })
    }

}
