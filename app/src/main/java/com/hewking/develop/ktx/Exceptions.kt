package com.hewking.develop.ktx

import android.content.Intent

/**
 * @Description: 对一些常用异常的简单封装
 * @Author: jianhao
 * @Date:   2021/3/24 15:21
 * @License: Copyright Since 2020 Hive Box Technology. All rights reserved.
 * @Notice: This content is limited to the internal circulation of Hive Box, 
 * and it is prohibited to leak or used for other commercial purposes.
 */

/** Throws an [IllegalStateException] with a message that includes [value]. */
fun unexpectedValue(value: Any?): Nothing = throw IllegalStateException("Unexpected value: $value")

/** Throws an [IllegalArgumentException] with the passed message. */
fun illegalArg(errorMessage: String? = null): Nothing = throw IllegalArgumentException(errorMessage)

/** Throws an [IllegalArgumentException] with the passed [argument]. */
fun illegalArg(
    argument: Any?
): Nothing = throw IllegalArgumentException("Illegal argument: $argument")

/** Throws an [UnsupportedOperationException] with the passed message. */
fun unsupported(
    errorMessage: String? = null
): Nothing = throw UnsupportedOperationException(errorMessage)

/** Throws an [UnsupportedOperationException] with the unsupported action name in the message. */
fun unsupportedAction(intent: Intent): Nothing = unsupported("Unsupported action: ${intent.action}")