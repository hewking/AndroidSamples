package com.hewking.develop.collection

import org.junit.Test

/**
 * @author: jianhao
 * @create: 2020/7/1
 * @description:
 */

class Test {

    @Test
    fun foo(){
        val arr = intArrayOf(2,4,6,3)

        val res = arr.all { it.rem(2) == 0 }

        println("res all is ord $res")
    }

    @Test
    fun testAny(){
        val arr = intArrayOf(2,4,4,3)
        val res = arr.any { it> 5 }
        println("res any is ${res}")
    }

}