package com.hewking.develop.basics

import org.junit.Test

/**
 * @Description: (用一句话描述该文件做什么)
 * @Author: jianhao
 * @Date:   2020/12/22 11:30
 * @License: Copyright Since 2020 Hive Box Technology. All rights reserved.
 * @Notice: This content is limited to the internal circulation of Hive Box, 
and it is prohibited to leak or used for other commercial purposes.
 */
class ObjectTestKt {

    data class Person(val name: String, val age : Int)

    @Test
    fun test(){
        var p: Person? = null
        print("name${p?.name?:""}")
    }

}