package com.hewking.develop.util

import android.content.ContentValues
import android.content.Context
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import androidx.core.os.EnvironmentCompat
import java.io.File
import java.io.IOException

/**
 *@Description:
 *@Author: jianhao
 *@Date:   2022-04-10 13:13
 *@License: Copyright Since 2020 Hive Box Technology. All rights reserved.
 *@Notice: This content is limited to the internal circulation of
 *Hive Box, and it is prohibited to leak or used for other commercial purposes.
 */
object PhotoUtil {

    /**
     * 创建图片地址uri,用于保存拍照后的照片 Android 10以后使用这种方法
     *
     * @param picName 图片名，注意：不能跟已存在的图片重名，否则获取的uri为空
     */
    private fun createImageUri(context: Context, picName: String): Uri? {
        val status = Environment.getExternalStorageState()
        val values = ContentValues()
        val mimeType = "image/jpeg"
        values.put(MediaStore.MediaColumns.DISPLAY_NAME, picName)
        values.put(MediaStore.MediaColumns.MIME_TYPE, mimeType)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            values.put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_PICTURES)
        } else {
            val storageDir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
            val path = (storageDir?.path + File.separator
                    + Environment.DIRECTORY_DCIM + File.separator + picName)
            values.put(
                MediaStore.MediaColumns.DATA,
                path
            )
        }

        // 判断是否有SD卡,优先使用SD卡存储,当没有SD卡时使用手机存储
        return if (status == Environment.MEDIA_MOUNTED) {
            context.contentResolver
                .insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values)
        } else {
            context.contentResolver
                .insert(MediaStore.Images.Media.INTERNAL_CONTENT_URI, values)
        }
    }

    /**
     * 创建保存图片的文件
     */
    @Throws(IOException::class)
    private fun createImageFile(context: Context, picName: String): File? {
        val storageDir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        if (!storageDir!!.exists()) {
            storageDir.mkdir()
        }
        val tempFile = File(storageDir, picName)
        return if (Environment.MEDIA_MOUNTED != EnvironmentCompat.getStorageState(tempFile)) {
            null
        } else tempFile
    }

}