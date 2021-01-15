package com.hewking.develop.demo

import com.hewking.develop.util.asMap
import com.hewking.develop.util.serializeToMap
import com.hewking.develop.util.toDataClass
import org.junit.Test

/**
 * @Description: (用一句话描述该文件做什么)
 * @Author: jianhao
 * @Date:   2020/12/23 17:25
 * @License: Copyright Since 2020 Hive Box Technology. All rights reserved.
 * @Notice: This content is limited to the internal circulation of Hive Box, 
and it is prohibited to leak or used for other commercial purposes.
 */
//example usage
data class Person(val name: String, val age: Int) {

}

class ObjectToMap  {

    @Test
    fun foo(){
        val person = Person("Tom Hanley", 99)

        val map = mapOf(
            "name" to "Tom Hanley",
            "age" to 99
        )

        val personAsMap: Map<String, Any> = person.serializeToMap()

        val mapAsPerson: Person = map.toDataClass()

        println(personAsMap)

        println(person.asMap())

    }

    @Test
    fun testReflect(){

    }

}