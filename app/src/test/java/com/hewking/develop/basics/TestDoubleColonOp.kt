package com.hewking.develop.basics

import org.junit.Test

/**
 * @author: jianhao
 * @create: 2020/7/21
 * @description: 测试双冒号操作符
 */

class TestDoubleColonOp {

    @Test
    fun test(){
        val res = foo(::add,3,4)
        println(res)
    }

    @Test
    fun test2(){
        val arr = arrayOf(3,4,6,7) // 明确arr数组类型是可以重载，isOdd方法的
//        val arr = arrayOf(3,4,6,7,"") // 无法明确arr数组类型，不能重载
        arr.filter(::isOdd)
    }

    fun foo(func: (Int,Int) -> Int,a: Int,b: Int): Int {
        return func(a,b)
    }

    fun add(a: Int, b : Int) = a + b

    fun isOdd(x: Int) = x.rem(2) == 0

    fun isOdd(s: String) = s == "brillig" || s == "slithy" || s == "tove"

}