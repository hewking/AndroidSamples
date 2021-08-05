package com.hewking.develop.coroutine.channel

import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.junit.Test

/**
 *@Description:
 *@Author: jianhao
 *@Date:   2021-07-06 17:58
 *@License: Copyright Since 2020 Hive Box Technology. All rights reserved.
 *@Notice: This content is limited to the internal circulation of
 *  Hive Box, and it is prohibited to leak or used for other commercial purposes.
 */
class ChannelTest {

    @Test
    fun test() = runBlocking {
        val channel  = Channel<Int>()
        launch {
            // this might be heavy CPU-consuming computation or async logic, we'll just send five squares
            for (x in 1..5) channel.send(x * x)
        }
        // here we print five received integers:
        repeat(5) { println(channel.receive()) }
        println("Done!")
    }

}