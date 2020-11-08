package com.hewking.develop.basics

import org.junit.Test

/**
 * @author: jianhao
 * @create: 2020/7/6
 * @description:
 */
class IntTest {

    @Test
    fun testRange(){

        5.rangeTo(6).forEach {
            println("num:$it")
        }

        val num = 4
        num.until(6).forEach {
            println("n2:$it")
        }

        (0 until 5).forEach {

        }

    }

    data class Rank(val rank : Int, val value: String)

    @Test
    fun test() {
        val arr = arrayOf(Rank(1,"hei"), Rank(4,"ya"), Rank(3,"la"))

        val res = arr.sortedBy { it.rank }

        println(res)

    }

}

public fun Int.zeroUntil(): IntRange {
    if (this <= 0) return IntRange.EMPTY
    return 0 until this
}