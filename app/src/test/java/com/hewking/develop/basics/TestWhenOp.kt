package com.hewking.develop.basics

import org.junit.Test

/**
 * @author: jianhao
 * @create: 2020/7/30
 * @description: 测试when 关键字
 */
class TestWhenOp {

    @Test
    fun test(){
//        foo(0)
        foo(1)
//        foo(2)
    }

    fun foo(code: Int){
        when(code) {
            0 -> println(0)
            1 -> println(1)
        }
    }

    fun quz(){

        val one = One()
        val two = Two()
        one.funobj = two::one

        bar(object: One(){
            override fun fun1() {
                super.fun1()
                println("fun1")
                ::test1.invoke()
            }

            override fun fun2() {
                super.fun2()
            }
        })
    }

    fun bar(parma: One) {

    }

    fun test1(){

    }

    class Two{
        fun one(){

        }
    }

    open class One {

        var funobj = {

        }

        open fun fun1(){
            println("fun1")
        }

        open fun fun2(){
            println("fun2")
        }

        open fun fun3(){
            println("fun3")
        }
    }

    interface Fly {
        fun fly()
    }

    open class Animal {

    }

    class Human: Animal(){

    }
    class Bird: Animal(),Fly{
        override fun fly() {
            TODO("Not yet implemented")
        }

    }

    class Person(bird: Fly) : Fly by dd{

    }


}

val dd = object : TestWhenOp.Fly {
    override fun fly() {
        TODO("Not yet implemented")
    }
}