package com.hewking.develop.util

import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.resume

/**
 * @Description:协程相关的扩展
 * @Author: 杨川博
 * @Date: 2020/10/23
 * @License: Copyright Since 2020 Hive Box Technology. All rights reserved.
 * @Notice: This content is limited to the internal circulation of Hive Box, 
 * and it is prohibited to leak or used for other commercial purposes.
 */
fun <T> CancellableContinuation<T>.complete(data: T) {
  if (isCompleted) return
  if (isActive) {
    resume(data)
  } else {
    cancel()
  }
}

/**
 * 切换至IO线程,除非await,其他情况不会阻塞后续语句执行,如果有多个withIO{}语句,会并行执行
 * val a = optAsync{ api.apiA() } // time cost: 5ms
 * val b = optAsync{ api.apiB() } // time cost: 6ms
 * val c = optAsync{ api.apiC() } // time cost: 7ms
 * val total = a.await() + b.await() + c.await() // total time cost: time cost: 7ms
 */
suspend fun <T> ioAsync(
    block: suspend CoroutineScope.() -> T): Deferred<T?> {
  return coroutineScope { optAsync(Dispatchers.IO, block) }
}

suspend fun <T> CoroutineScope.optAsync(coroutineContext: CoroutineContext,
                                        block: suspend CoroutineScope.() -> T): Deferred<T?> {
  return coroutineScope {
    async(coroutineContext) {
      try {
        block.invoke(this@optAsync)
      } catch (e: Exception) {
        e.printStackTrace()
        null
      }
    }
  }
}
