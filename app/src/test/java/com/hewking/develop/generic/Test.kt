package com.hewking.develop.generic

/**
 * @author: jianhao
 * @create: 2020/6/19
 * @description:
 */
class Test {

    class A {

    }

    fun <T> foo(){

    }

    fun baz(){
        this.foo<String>()
    }

}

inline fun <reified T> bar(): T{
    return T::class.java.newInstance()
}

fun foo(){
    bar<String>()
}

fun foo2():String {
    return bar<String>()
}

