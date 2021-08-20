package com.hewking.develop.coroutine
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.suspendCancellableCoroutine
import org.junit.Test
import java.lang.IllegalArgumentException
import kotlin.coroutines.resumeWithException

/**
 *@Description:
 *@Author: jianhao
 *@Date:   2021-08-13 11:16
 *@License: Copyright Since 2020 Hive Box Technology. All rights reserved.
 *@Notice: This content is limited to the internal circulation of
 *  Hive Box, and it is prohibited to leak or used for other commercial purposes.
 */
class SuspendCoroutineTest {

    @Test
    fun `test resumeWithException`() = runBlocking{
        val url = uploadAvatar("")
    }

    suspend fun uploadAvatar(img: String): String  = suspendCancellableCoroutine{
        if (img.isEmpty()) {
            it.resumeWithException(IllegalArgumentException("文件不存在"))
        } else {
            it.resume("上传成功",null)
        }
    }

}