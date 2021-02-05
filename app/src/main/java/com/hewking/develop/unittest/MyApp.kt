package com.hewking.develop.unittest

import android.app.Application

/**
 * @Description: 为单元测试提供Application 环境
 * @Author: jianhao
 * @Date:   2021/2/5 18:01
 * @License: Copyright Since 2020 Hive Box Technology. All rights reserved.
 * @Notice: This content is limited to the internal circulation of Hive Box, 
and it is prohibited to leak or used for other commercial purposes.
 */
class MyApp: Application() {

    override fun onCreate() {
        super.onCreate()
        instance = this
    }

    companion object {
        lateinit var instance: Application
    }

}