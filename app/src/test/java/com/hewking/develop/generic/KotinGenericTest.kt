package com.hewking.develop.generic

import org.junit.Test
import java.util.ArrayList


open class AA {
    open fun say(){
        println("hello")
    }
}

class BB : AA(){
    override fun say() {
        println("${getClassName()}: hi")
    }
}

class CC<out T>{

}

/**
 * refired 只能在inline函数中使用，可以直接操作T得值
 */
inline fun <reified T> T.getClassName():String{
    return T::class.java.simpleName
}

// 这里的out T 的意思是这里的类型T只能用于作为生产者，也就是作为返回值
interface GFoo<out T>{
    fun bar(): T
}

// 这里的in T的意思是这里的类型T只能作为消费者，也就是参数
interface GFoo2<in T> {
    fun bar(t: T)
}

// out修饰的类型参数 只能用于class or interface
//fun <out T> gfoo(){
//
//}

class KotinGenericTest {

    @Test
    fun test(){
        val list = mutableListOf<BB>()
        foo(list)
        foo2(list)

        bar(mutableListOf<Any>())
        bar2(mutableListOf<Any>())
        bar3(listOf<Any>())
        // 1. 注意一下，如果左边是List 而不是MutabbleList 是不需要泛型协变支持的
        // 原因是List只支持读取，而不是修改，是生产者
        val bb: List<AA> = mutableListOf<BB>(BB())
        val aa : MutableList<out AA> = mutableListOf<BB>(BB())

        // 消费者的例子
        val cc: MutableList<in AA> = mutableListOf<Any>()

        val l1 : List<*> = list
        val l2: MutableList<*> = list
//        l2.add(BB()) : error
    }

    /**
     * 生产者
     */
    fun foo(a: MutableList<out AA>){
        a.forEach {
            it.say()
        }
    }

    fun foo2(a: List<AA>){

    }

    /**
     * 消费者
     */
    fun bar(b: MutableList<in AA>){
    }

    fun bar2(bb: MutableList<in AA>){

    }

    fun bar3(bb: List<*>){
        // * 相当于 out Any
    }

}