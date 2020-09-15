package com.hewking.develop.util

import android.util.Log

/**
 * @Description: 对log的简单封装
 * @Author: jianhao
 * @Date:   2020/9/15 10:39
 * @License: Copyright Since 2020 Hive Box Technology. All rights reserved.
 * @Notice: This content is limited to the internal circulation of Hive Box, and it is prohibited to leak or used for other commercial purposes.
 */

object Logger {

    fun debug(tag: String, vararg payload: String) {
        val log = payload.reduce { acc, s -> "$acc $s" }
        Log.d(tag, log)
    }

}