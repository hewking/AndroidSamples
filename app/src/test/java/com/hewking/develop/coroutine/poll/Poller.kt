package com.hewking.develop.coroutine.poll

import kotlinx.coroutines.flow.Flow

/**
 * @author: jianhao
 * @create: 2020/7/21
 * @description:
 */
interface Poller<T>{

    fun poll(delay: Long): Flow<T>

    fun close()

}