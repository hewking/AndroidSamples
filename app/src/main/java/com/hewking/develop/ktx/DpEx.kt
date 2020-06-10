package com.hewking.develop.ktx

import android.content.res.Resources
import android.util.TypedValue

/**
 *
 * Email:angcyo@126.com
 * @author angcyo
 * @date 2019/12/20
 * Copyright (c) 2019 ShenZhen O&M Cloud Co., Ltd. All rights reserved.
 */

val density: Float get() = Resources.getSystem()?.displayMetrics?.density ?: 0f
val dp: Float get() = Resources.getSystem()?.displayMetrics?.density ?: 0f
val dpi: Int get() = Resources.getSystem()?.displayMetrics?.density?.toInt() ?: 0

fun Int.toDp(): Float {
    return this * dp
}

fun Int.toDpi(): Int {
    return this * dpi
}

fun Float.toDp(): Float {
    return this * dp
}

fun Float.toDpi(): Int {
    return (this * dpi).toInt()
}

fun dp2px(dp: Float): Float {
    val r = Resources.getSystem()
    return TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        dp,
        r.displayMetrics
    )
}