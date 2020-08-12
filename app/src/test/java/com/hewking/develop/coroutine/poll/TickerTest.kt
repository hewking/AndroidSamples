package com.hewking.develop.coroutine.poll

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.TickerMode
import kotlinx.coroutines.channels.ticker
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.junit.Test
import java.time.LocalDate
import java.time.LocalDateTime
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

/**
 * @author: jianhao
 * @create: 2020/7/21
 * @description:
 */

//fun main() = runBlocking<Unit> {
//
//    val tickerChannel = ticker(delayMillis = 1000, initialDelayMillis = 0)
//
//    // 每秒打印
//    for (unit in tickerChannel) {
//        System.err.println("unit = $unit")
//    }
//}

class TickerTest {

    val tickerChannel = ticker(delayMillis = 1000L, initialDelayMillis = 0)

    @Test
    fun test() {
        // 在GlobalScope中ticker 无效
//        GlobalScope.launch {
//            val tickerChannel = ticker(delayMillis = 1000L, initialDelayMillis = 0)
//
//            for (event in tickerChannel) {
//                val currentTime = LocalDateTime.now()
//                println("currentTime:$currentTime")
//            }
//            delay(51000L)
//            tickerChannel.cancel()
//        }

        // 阻塞上下文中有效
        runBlocking {

            launch {
                for (event in tickerChannel) {
                    val currentTime = LocalDateTime.now()
                    println("currentTime:$currentTime")
                }
            }

            delay(5000L)
            tickerChannel.cancel()
        }


    }

}