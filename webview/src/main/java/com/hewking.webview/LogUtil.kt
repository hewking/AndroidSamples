package com.hewking.webview

import android.util.Log

/**
 *@Description:
 *@Author: jianhao
 *@Date:   2021-09-14 18:12
 *@License: Copyright Since 2020 Hive Box Technology. All rights reserved.
 *@Notice: This content is limited to the internal circulation of
 *  Hive Box, and it is prohibited to leak or used for other commercial purposes.
 */
object LogUtil {

    fun e(tag: String, message: String){
        Log.e(tag, message)
    }

}