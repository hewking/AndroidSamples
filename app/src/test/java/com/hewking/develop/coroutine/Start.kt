package com.hewking.develop.coroutine

import org.junit.Test
import kotlin.coroutines.*

/**
 * @Description: (用一句话描述该文件做什么)
 * @Author: jianhao
 * @Date:   2020/11/15 21:23
 * @License: Copyright Since 2020 Hive Box Technology. All rights reserved.
 * @Notice: This content is limited to the internal circulation of Hive Box, and it is prohibited to leak or used for other commercial purposes.
 */

class Start {


    @Test
    fun foo(){

        val continuation = suspend {
            ""
        }.createCoroutine(object : Continuation<Any> {
            override val context: CoroutineContext = EmptyCoroutineContext

            override fun resumeWith(result: Result<Any>) {
                print("i'm coroutine")
            }

        })

        continuation.resume(Unit)

    }

}

