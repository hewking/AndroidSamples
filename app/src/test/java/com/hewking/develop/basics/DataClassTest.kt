package com.hewking.develop.basics

import com.google.gson.Gson
import org.junit.Test

/**
 * @author: jianhao
 * @create: 2020/7/3
 * @description:
 */
class DataClassTest {

    @Test
    fun test(){

        val p = Gson().fromJson(
                "{\"age\":\"13\"}",Person::class.java)

        println("name: ${p.name?.length} age: ${p.age}")

        // data class的解构赋值
        val (age) = p

        // copy 函数只更改某一个值的时候
        val p2 = p.copy(name="hanhan")

        println("p2 :${p2}")

        /**
         * 经过demo测试 在编写data class 用于gson解析时
         * 1. 需要一个默认的构造方法，这样就需要所有的 字段都要赋默认值
         * 2. 不确定会有的的值需要加 ? ,这样服务端没有返回才不会出现使用时报NPE异常
         * 3. 加 ？的字段，可以不用赋值null,因为?本身就已经有一个null值了
         */

    }

    data class Person(val name:String? = "fff",val age: Int = 1) {
//        constructor():this("",12){
//
//        }
    }

    @Test
    fun `test in list`(){
        val list = kotlin.collections.listOf<String>("3","2","1")
        kotlin.io.println("3" !in list)
        kotlin.io.println("4" !in list)

        println(list.contains("3"))
        println(!list.contains("3"))
    }

}