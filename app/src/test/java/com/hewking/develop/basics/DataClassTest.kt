package com.hewking.develop.basics

import com.google.gson.Gson
import org.junit.Test

/**
 * @author: jianhao
 * @create: 2020/7/3
 * @description:
 */
class DataClassTest {

    @Test
    fun test(){

        val p = Gson().fromJson(
                "{\"age\":\"13\"}",Person::class.java)

        println("name: ${p.name?.length} age: ${p.age}")

        val (age) = p

        val p2 = p.copy(name="hanhan")

        println("p2 :${p2}")

    }

    data class Person(val name:String? = "fff",val age: Int = 1) {
//        constructor():this("",12){
//
//        }
    }

}