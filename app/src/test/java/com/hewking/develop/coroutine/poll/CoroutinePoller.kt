package com.hewking.develop.coroutine.poll

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow

/**
 * @author: jianhao
 * @create: 2020/7/21
 * @description:
 */

data class Data(val name: String = "zhangsan")

class CoroutinePoller(val dispatcher: CoroutineDispatcher) : Poller<Data>{


    override fun poll(delay: Long): Flow<Data> {
        return channelFlow {
            while (true) {
                delay(delay)
            }
        }
    }

    override fun close() {
        dispatcher.cancel()
    }


}