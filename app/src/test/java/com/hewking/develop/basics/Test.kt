package com.hewking.develop.basics

/**
 * @author: jianhao
 * @create: 2020/6/22
 * @description:
 */

fun foo(name:String,age:Int = 12){

}

interface A{

    fun bar(name:String,age:Int = 12)

}

class B : A {
    override fun bar(name: String, age: Int) {
        TODO("Not yet implemented")
    }
}

fun test (){
    B().bar(name="")

}