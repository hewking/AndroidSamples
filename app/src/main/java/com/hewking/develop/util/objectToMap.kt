package com.hewking.develop.util

/**
 * @Description: 用于Kotlin 对象转map,
 * [reference](https://stackoverflow.com/questions/49860916/how-to-convert-a-kotlin-data-class-object-to-map)
 * @Author: jianhao
 * @Date:   2020/12/23 17:23
 * @License: Copyright Since 2020 Hive Box Technology. All rights reserved.
 * @Notice: This content is limited to the internal circulation of Hive Box, 
and it is prohibited to leak or used for other commercial purposes.
 */
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.math.BigDecimal
import kotlin.reflect.KClass
import kotlin.reflect.full.memberProperties

val gson = Gson()

//convert a data class to a map
fun <T> T.serializeToMap(): Map<String, Any> {
    return convert()
}

//convert a map to a data class
inline fun <reified T> Map<String, Any>.toDataClass(): T {
    return convert()
}

//convert an object of type I to type O
inline fun <I, reified O> I.convert(): O {
    val json = gson.toJson(this)
    return gson.fromJson(json, object : TypeToken<O>() {}.type)
}

/**
 * 使用反射的方式把对象转化为Map
 */
inline fun <reified T : Any> T.asMap() : Map<String, Any?> {
    val props = T::class.memberProperties.associateBy { it.name }
    return props.keys.associateWith { props[it]?.get(this) }
}