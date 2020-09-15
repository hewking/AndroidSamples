package com.hewking.develop.util

import android.app.Activity
import android.content.Context
import android.widget.Toast

/**
 * @Description: Toast 的简单封装
 * @Author: jianhao
 * @Date:   2020/9/15 10:34
 * @License: Copyright Since 2020 Hive Box Technology. All rights reserved.
 * @Notice: This content is limited to the internal circulation of Hive Box, and it is prohibited to leak or used for other commercial purposes.
 */

fun toast(ctx: Context, text: String) {
    Toast.makeText(ctx, text, Toast.LENGTH_SHORT).show()
}

fun Activity.toast(text: String) {
    toast(this, text)
}