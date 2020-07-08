package com.hewking.develop.basics

import org.junit.Test

/**
 * @author: jianhao
 * @create: 2020/7/8
 * @description:
 */
class TestElivsExp {

    @Test
    fun test(){
        foo2(null)
    }

    fun foo(pair: String?){
        println("before")
        pair?:return

        println("heiheihei")
    }

    fun foo2(pair: String?){
        println("before")
        pair?: kotlin.run {
            println("内部做一点操作")
            return
        }

        println("heiheihei")
    }

}