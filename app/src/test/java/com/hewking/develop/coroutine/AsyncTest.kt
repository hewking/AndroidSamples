package com.hewking.develop.coroutine

import kotlinx.coroutines.*
import org.junit.Test
import java.lang.ArithmeticException
import java.util.*
import java.util.Arrays.fill
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
        } catch (e: ArithmeticException) {
            e.printStackTrace()
        }
    }

    suspend fun one(): Int {
        delay(1500)
        return 1
    }

    suspend fun two(): Int {
        delay(1500)
        return 2
    }

    @Test
    fun testAsyncRequest() {
        GlobalScope.launch {

            /*measureTimeMillis返回给定的block代码的执行时间*/
            val time = measureTimeMillis {
                val sum = withContext(Dispatchers.IO) {
                    val one = async { one() }
                    val two = async { two() }
                    val res = one.await() + two.await()
                    res
                }
                println("两个方法返回值的和：${sum}")
            }
            println("执行耗时：${time}")
        }
        println("----------------")
        /*应为上面的协程代码并不会阻塞掉线程，所以我们这里让线程睡4秒，保证线程的存活，在实际的Android开发中无需这么做*/
        Thread.sleep(4000)
    }

    @Test
    fun testAsyncRequest2() {
        GlobalScope.launch {
            /*measureTimeMillis返回给定的block代码的执行时间*/
            val time = measureTimeMillis {
                val sum = withContext(Dispatchers.IO) {
                    var result = 0
                    val defferedlist = mutableListOf<Deferred<Int>>()
                    for(i in 0 until 10) {
                    val one = async { one() }
                        defferedlist.add(one)
//                    val two = async { two() }
//                    result += one.await() + two.await()
                    }
                    result = defferedlist.map { it.await() }.reduce { acc, i -> acc + i }
                    result
                }
                println("两个方法返回值的和：${sum}")
            }
            println("执行耗时：${time}")
        }
        println("----------------")
        /*应为上面的协程代码并不会阻塞掉线程，所以我们这里让线程睡4秒，保证线程的存活，在实际的Android开发中无需这么做*/
        Thread.sleep(4000)
    }

    @Test
    fun testAsyncRequest3() {
        GlobalScope.launch {
            /*measureTimeMillis返回给定的block代码的执行时间*/
            var result = 0
            val time = measureTimeMillis {
                withContext(Dispatchers.IO) {
                    val array = IntArray(10).also {
                        it.fill(10)
                    }

                    val defferedlist = array.map { async { one() } }

                    defferedlist.forEach {
                        result += it.await()
                    }

                }
                println("两个方法返回值的和：${result}")
            }
            println("执行耗时：${time}")
        }
        println("----------------")
        Thread.sleep(4000)
    }



}

