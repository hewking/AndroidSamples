package com.hewking.develop.demo.hash

import com.google.common.hash.Hashing
import org.junit.Test
import java.nio.charset.Charset
import java.util.*

/**
 * @Description: (用一句话描述该文件做什么)
 * @Author: jianhao
 * @Date:   2021/1/27 15:47
 * @License: Copyright Since 2020 Hive Box Technology. All rights reserved.
 * @Notice: This content is limited to the internal circulation of Hive Box, 
and it is prohibited to leak or used for other commercial purposes.
 */
class TestHash {

    data class PhoneNumber(
        val phone:String,
        val prefix: String,
        val areaCode: Int
    )

    @Test
    fun test() {

        val phone = PhoneNumber("222222", "+86", 86)

        val hash1 = Objects.hash(phone)
        val hash2 = Objects.hash(phone.phone, phone.prefix)

        val hashString = Hashing.adler32().hashString(phone.toString(), Charset.defaultCharset())

        println("hash1 $hash1")
        println("hash2 $hash2")
        println("hash3 $hashString")


    }

}