package com.hewking.develop.demo

import org.junit.Test

/**
 * @author: jianhao
 * @create: 2020/6/19
 * @description:
 */
class Test {

    data class SecureCheckResult(
        val code: Int,
        val data: SecureCheckData,
        val msg: String
    )

    data class SecureCheckData(
        val clientIp: String,
        val key1: String,
        val key2: String,
        val key3: String,
        val key4: String,
        val key5: String,
        val keyOrder: String,
        val needSliderCode: Boolean,
        val requestCode: String,
        val timestamp: Long
    ) {
        fun getRSAPublicKey():String {
            val keyArray = arrayListOf(key1,key2,key3,key4,key5)
            return keyOrder.split(',').map { keyArray[it.toInt() - 1] }.reduce { acc, s ->
                acc + s
            }
        }
    }

    @Test
    fun test(){
        val data = SecureCheckData("192.168.0.1","aaaa",
        "bsbbb","cccc","dddd",
        "eeee","1,2,3,4,5,1,2",true,"12345",
        System.currentTimeMillis())

        println(data.getRSAPublicKey())
    }

}