package com.hewking.develop.coroutine

import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withTimeoutOrNull
import org.junit.Test

/**
 * @Description: (用一句话描述该文件做什么)
 * @Author: jianhao
 * @Date:   2021/3/15 15:04
 * @License: Copyright Since 2020 Hive Box Technology. All rights reserved.
 * @Notice: This content is limited to the internal circulation of Hive Box, 
and it is prohibited to leak or used for other commercial purposes.
 */
class TimeoutTest {

    @Test
    fun test(){
        runBlocking {

            val result = withTimeoutOrNull(3000L, {
                delay(2000L)
                println("执行")
                "结果"
            })

            println("result $result")

            delay(1000L)
            val resul2 = withTimeoutOrNull(3000L, {
                delay(5000L)
                println("执行")
                "结果"
            })

            println("result1 : $resul2")

        }

    }

}