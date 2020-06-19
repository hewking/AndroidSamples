package com.hewking.develop.coroutine

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.junit.Test
import java.io.ByteArrayOutputStream
import java.net.HttpURLConnection
import java.net.URL
import java.util.*
import java.util.stream.Collector
import java.util.stream.Collectors

class CoroutineTest {

    @Test
    fun test(){
        println("hello world")
        GlobalScope.launch {
            println("start" + Thread.currentThread().name)
            foo()
            println("end" + Thread.currentThread().name)
        }
        Thread.sleep(5000)
        println("end")
    }

    suspend fun foo(){
        val res = withContext(Dispatchers.IO) {
            println("fetch start" + Thread.currentThread().name)
            val result = fetchWeather()
            println("fetch end" + Thread.currentThread().name)
            result
        }
        println("foo result : ${res}")
    }

    fun fetchWeather():String{
        val conn = URL("http://t.weather.sojson.com/api/weather/city/101030100").openConnection() as HttpURLConnection
        conn.connect()
        println(conn.responseCode)
        if (conn.responseCode == 200) {

            val result = conn.inputStream.bufferedReader().lines().parallel().collect(Collectors.joining(System.lineSeparator()))
//            print(result)
        return result
        }
        return "-1"
    }

}