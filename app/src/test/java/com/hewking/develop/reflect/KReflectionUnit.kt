package com.hewking.develop.reflect

import org.junit.Test

/**
 * @Description: (用一句话描述该文件做什么)
 * @Author: jianhao
 * @Date:   2020/12/25 16:47
 * @License: Copyright Since 2020 Hive Box Technology. All rights reserved.
 * @Notice: This content is limited to the internal circulation of Hive Box, 
and it is prohibited to leak or used for other commercial purposes.
 */
class KReflectionUnit {

    @Test
    fun test(){

        val name = "tom"

        val funLen = name::length

        println(funLen.invoke())

    }

    fun test2(){
        val list = listOf(2,3)
        list.flatMap { listOf(2) }
    }

}