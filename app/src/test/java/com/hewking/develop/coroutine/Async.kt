package com.hewking.develop.coroutine

import okhttp3.Call
import okhttp3.Callback
import okhttp3.Response
import retrofit2.HttpException
import java.io.IOException
import kotlin.coroutines.*

/**
 * @Description: (用一句话描述该文件做什么)
 * @Author: jianhao
 * @Date:   2020/11/15 17:35
 * @License: Copyright Since 2020 Hive Box Technology. All rights reserved.
 * @Notice: This content is limited to the internal circulation of Hive Box, and it is prohibited to leak or used for other commercial purposes.
 */

interface AsyncScrope

suspend fun async(
    context: CoroutineContext = EmptyCoroutineContext,
    block: suspend AsyncScrope.() -> Unit
){
    val completion = AsyncCoroutine()
    block.startCoroutine(completion, completion)
}

class AsyncCoroutine(
    override val context: CoroutineContext = EmptyCoroutineContext
): Continuation<Unit>, AsyncScrope {
    override fun resumeWith(result: Result<Unit>) {
        return result.getOrThrow()
    }
}

suspend fun <T> AsyncCoroutine.await(block: () ->Call) = suspendCoroutine<T> {
    continuation ->
    val call = block()
    call.enqueue(object : Callback{
        override fun onFailure(call: Call, e: IOException) {
            continuation.resumeWithException(e)
        }

        override fun onResponse(call: Call, response: Response) {
            if (response.isSuccessful) {
                response.body?.let{
//                    continuation.resume(it)
                }
            } else {
//                continuation.resumeWithException(HttpException(response))
            }
        }

    })

}