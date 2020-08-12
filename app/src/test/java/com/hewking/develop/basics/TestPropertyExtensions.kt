package com.hewking.develop.basics

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.junit.Test

/**
 * @author: jianhao
 * @create: 2020/7/24
 * @description: 拓展属性的一些demo
 */

class TestPropertyExtensions {

    @Test
    fun test(){
        runBlocking {
            val n = Name("mary")
            delay(1000)

            println(n.nick)
            n.nick = "niupi"
            delay(1000)
            println(n.nick)

        }
    }

}

data class Name(var name: String = "tom")

var Name.nick: String
    get() {
        return this.name + ": yoyo"
    }
    set(value) {
        name = value
    }