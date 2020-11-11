package com.hewking.develop.basics

import com.hewking.develop.util.UnitUtil
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

    @Test
    fun foo(){
        val res = UnitUtil.addThousandSeparator("--")
        println(res)
    }

}