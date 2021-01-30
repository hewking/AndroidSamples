package com.hewking.develop.basics

import android.text.TextUtils
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
        var text: String? = null

        println(text.isNullOrEmpty())
        // result: null


        println(text?.isNotEmpty() != true)

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