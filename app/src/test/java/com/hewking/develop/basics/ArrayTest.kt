package com.hewking.develop.basics

import org.junit.Test

/**
 * @Description: (用一句话描述该文件做什么)
 * @Author: jianhao
 * @Date:   2020/12/8 16:52
 * @License: Copyright Since 2020 Hive Box Technology. All rights reserved.
 * @Notice: This content is limited to the internal circulation of Hive Box, 
and it is prohibited to leak or used for other commercial purposes.
 */
class ArrayTest {

    @Test
    fun foo(){
        arrayOf(3,2,4,5).joinToString {
            it.toString()
        }.also {
            println("res: $it")
        }
    }

    @Test
    fun `test array set element null`(){
        val arr = Array<String?>(5) {null}
        println(arr.toList().size)

    }

}