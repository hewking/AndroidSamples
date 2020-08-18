package com.hewking.develop.basics

import org.junit.Test

/**
 * @author: jianhao
 * @create: 2020/7/30
 * @description:
 */
class StringTest {

    @Test
    fun test(){

    }

    @Test
    fun testSlice(){
        "1234".slice(3 until  4).also {
            println(it)
        }
    }

}