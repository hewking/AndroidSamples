package com.hewking.develop.generic

import org.junit.Test

/**
 *@Description: (用一句话描述该文件做什么)
 *@Author: jianhao
 *@Date:   2020/10/27 10:56 PM
 *@License: Copyright Since 2020 Hive Box Technology. All rights reserved.
 *@Notice: This content is limited to the internal circulation of Hive Box,
 and it is prohibited to leak or used for other commercial purposes.
 */

interface Animal<DataType> {

    fun walk()

    fun eat(dataType: DataType)

}

class Water

class Human : Animal<Water> {
    override fun walk() {
        TODO("Not yet implemented")
    }

    override fun eat(dataType: Water) {
        TODO("Not yet implemented")
    }

}

class GenericClassTest {

    @Test
    fun foo(){
        val clazz: Class<out Animal<out Any>> = Human::class.java
//        clazz.newInstance().eat(Water())
    }

}