package com.hewking.develop.coroutine

import kotlinx.coroutines.*
import org.junit.Test
import java.lang.ArithmeticException
import kotlin.system.measureTimeMillis

/**
 * @author: jianhao
 * @create: 2020/7/3
 * @description:
 */
class AsyncTest {

    @Test
    fun foo() {
        main()
    }


    suspend fun doSomethingUsefulOne(): Int {
        delay(1000L)
        return 10
    }

    suspend fun doSomethingUsefulTwo(): Int {
        delay(1000L)
        return 20
    }

    fun main() = runBlocking {
        val time = measureTimeMillis {
            val t1 = doSomethingUsefulOne()
            val t2 = doSomethingUsefulTwo()
            println("the answer is ${t1 + t2}")
        }
        println("cost time is : $time")
    }

    @Test
    fun bar() = runBlocking {
        val time = measureTimeMillis {
            val t1 = async { doSomethingUsefulOne() }
            val t2 = async { doSomethingUsefulTwo() }
            println("the answer is : ${t1.await() + t2.await()}")
        }
        println("cost time is : ${time}")
    }

    @Test
    fun baz() = runBlocking {
        val time = measureTimeMillis {
            val t1 = async(start = CoroutineStart.LAZY) {
                doSomethingUsefulTwo()
            }
            val t2 = async(start = CoroutineStart.LAZY) { doSomethingUsefulOne() }
            // 惰性求值 ，需要start 才会启动携程
            t1.start()
            t2.start()
            println("the answer is : ${t1.await() + t2.await()}")
        }
        println("the time is : $time")
    }

    suspend fun concurrentSum() = coroutineScope {
        val t1 = async { doSomethingUsefulOne() }
        val t2 = async { doSomethingUsefulTwo() }
        t1.await() + t2.await()
    }

    suspend fun concurrentSumError() = coroutineScope {
        val t1 = async {
            try {
                delay(Long.MAX_VALUE)
                doSomethingUsefulOne()
            } finally {
                println("one job is cnacelled")
            }
        }

        val t2 = async<Int> {
            println("job 2 is start")
            throw ArithmeticException()
        }
        t1.await() + t2.await()

    }

    @Test
    fun testException() = runBlocking {
        try {
            concurrentSumError()
            Unit
        } catch (e: ArithmeticException){
            e.printStackTrace()
        }
    }

}

