package com.hewking.develop.demo.synthetic

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import org.junit.Test
import java.io.FileInputStream
import java.io.FileOutputStream
import javax.xml.parsers.DocumentBuilderFactory

/**
 *@Description:
 *@Author: jianhao
 *@Date:   2021-09-03 15:09
 *@License: Copyright Since 2020 Hive Box Technology. All rights reserved.
 *@Notice: This content is limited to the internal circulation of
 *  Hive Box, and it is prohibited to leak or used for other commercial purposes.
 */
class Synthetic2ViewBinding {

    @Test
    fun test(){
        val path = "E:\\private_project\\AndroidDeveloper\\app\\src\\main\\res\\layout\\activity_main.xml"
        val parserHandler = XmlPullParserHandler()
        val fis = FileInputStream(path)
        val ids = parserHandler.parse(fis)
        println(ids)

    }

}