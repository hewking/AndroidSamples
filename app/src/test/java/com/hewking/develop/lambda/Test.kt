package com.hewking.develop.lambda

import kotlin.contracts.contract

/**
 * @author: jianhao
 * @create: 2020/6/19
 * @description:
 */

interface Callback {
    fun onSuccess(result: Int)
}

abstract class Base {
    private var id: Int = 0

    abstract fun base()

    fun base2(){

    }
}

typealias func2 = () -> Int

class Test {

    fun foo(){
        baz(OnClickListener {

        })

        bar(object: Callback{
            override fun onSuccess(result: Int) {
                TODO("Not yet implemented")
            }

        })

        quz(object : Base(){
            override fun base() {
                TODO("Not yet implemented")
            }

        })

        // 从以上对比可以看出，kotlin 把java的但函数接口(SAM)当作函数类型了
        foo2 {
            2
        }

        foo3 {
            2
        }

        baz2(::baz)

    }

    fun baz(listener: OnClickListener){

    }

    fun bar(callback: Callback){

    }

    fun quz(base: Base){

    }

    fun foo2(func: ()-> Int){

    }

    fun foo3(func: func2){
        func()
    }

    fun baz2(func: (listener: OnClickListener) -> Unit){

    }

    val quzFunc: (Int) -> Unit = {

    }

}

inline fun <T> T.apply2(block: T.() -> Unit){
        block()
}