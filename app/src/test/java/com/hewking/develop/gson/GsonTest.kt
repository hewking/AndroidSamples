package com.hewking.develop.gson

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import org.junit.Before
import org.junit.Test

/**
 * @author: jianhao
 * @create: 2020/8/14
 * @description:
 */
class GsonTest {

    lateinit var gson: Gson

    @Before
    fun init(){
        gson = GsonBuilder().create()
    }

    @Test
    fun test(){
        val p = Person()

        val map: Map<*,*> = gson.fromJson(gson.toJson(p),Map::class.java)

        println(map)
    }

    class Person {
        var name = "heihei"
        var age = 13
        var set = 1
    }

}