package com.hewking.develop.basics

import org.junit.Test

/**
 * @author: jianhao
 * @create: 2020/7/15
 * @description: 测试一些Kotlin中的操作符
 */

class OperatorTest {

    @Test
    fun testElvisOp(){
        var obj: Any? = null

        obj?.run {
            println("not null")
        }?:run {
            println("i am null")
            return
        }

        println("end")
    }

}