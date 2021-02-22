package com.hewking.develop.unittest.rebolectric

import com.hewking.develop.unittest.MyApp
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

/**
 * @Description: 单元测试用例
 * @Author: jianhao
 * @Date:   2021/2/5 17:59
 * @License: Copyright Since 2020 Hive Box Technology. All rights reserved.
 * @Notice: This content is limited to the internal circulation of Hive Box, 
and it is prohibited to leak or used for other commercial purposes.
 */
@RunWith(RobolectricTestRunner::class)
@Config(sdk = [26], application = MyApp::class)
class RoboletricTest {

    @Before
    fun setup(){

    }

    @Test
    fun `test hello`(){
        println("test hello")
    }

    @After
    fun tearDown(){

    }

}