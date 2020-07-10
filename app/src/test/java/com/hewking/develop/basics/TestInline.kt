package com.hewking.develop.basics

import org.junit.Test

/**
 * @author: jianhao
 * @create: 2020/7/8
 * @description: 测试inline 关键字
 */

class TestInline {

    @Test
    fun test(){
        bar {
            "怪额"
        }

        qur {
            "牛皮"
        }
    }

    fun foo(){

    }

    /**
     * inline 对于参数带有函数类型的方法来说很值得优化
     */
    inline fun bar(baz: () -> String){
        println("bar:${baz()}")
    }

    fun qur(say: () -> String) {
        println("qur:${say()}")
    }

}