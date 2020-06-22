package com.hewking.develop.coroutine

import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.junit.Test
import kotlin.concurrent.thread
import kotlin.random.Random

/**
 * @author: jianhao
 * @create: 2020/6/19
 * @description:
 */
class DeferredTest {

    @Test
    fun test(){
        GlobalScope.launch {
            val res = foo().await()
            println("res:$res")
        }
        Thread.sleep(4000L)
    }

    fun foo(): Deferred<String>{
        val deferred = CompletableDeferred<String>()
        thread {
            Thread.sleep(2000L)
//            if (Random.nextBoolean()) {
//                deferred.complete("sucess")
//            } else {
//                deferred.cancel()
//            }
            deferred.cancel()
        }
        return deferred
    }

}