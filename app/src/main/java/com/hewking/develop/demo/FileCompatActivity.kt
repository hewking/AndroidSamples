package com.hewking.develop.demo

import android.app.Activity
import android.content.Intent
import android.database.Cursor
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import com.hewking.develop.R
import com.hewking.develop.util.Logger
import kotlinx.android.synthetic.main.activity_file_compat.*
import okhttp3.internal.closeQuietly
import java.io.File
import java.io.FileOutputStream


class FileCompatActivity : AppCompatActivity() {

    private val IMAGE_PROJECTION = arrayOf(
        MediaStore.Images.Media.DISPLAY_NAME,
        MediaStore.Images.Media.SIZE,
        MediaStore.Images.Media._ID
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_file_compat)

        getFileDir.setOnClickListener {
            val fileDirPath = this.filesDir.absolutePath
            val cacheDirPath = cacheDir.absolutePath
            val externalCachePath = externalCacheDir?.absolutePath
            val externaFilePath = getExternalFilesDir(Environment.DIRECTORY_DCIM)?.absolutePath
            val externalStorageDirectory = Environment.getExternalStorageDirectory().absolutePath
            val dataDirPath = Environment.getDataDirectory().absolutePath
            val rootDirPath = Environment.getRootDirectory().absolutePath
            val downloadCacheDirPath = Environment.getDownloadCacheDirectory().absolutePath
            val externalStoragePublicDirectoryPath =
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).absolutePath
            Logger.debug(
                "getFilePath", "fileDirPath:$fileDirPath",
                "cacheDirPath:$cacheDirPath",
                "externalCachePath:$externalCachePath",
                "externalFilePath:$externaFilePath"
            )

            Logger.debug(
                "environmentPath", "externalStorageDirectory:$externalStorageDirectory",
                "dataDirPath: $dataDirPath",
                "rootDirPath:$rootDirPath",
                "downloadCacheDirPath:$downloadCacheDirPath",
                "externalStoragePublicDirectoryPath:$externalStoragePublicDirectoryPath"
            )
        }

        btnSaveImage.setOnClickListener {
            // 直接通过路径去存储是可以的，不应该有这样的代码出现
            var path = getExternalFilesDir(Environment.DIRECTORY_DCIM)
//            if (Environment.isExternalStorageLegacy()) {
//                path = File("/storage/emulated/0")
//            }
            val dir = File(path, "AndroidDeveloper")
            if (!dir.exists()) {
                dir.mkdir()
            }
            val file = File(dir, "image.png")
            val bitmap = BitmapFactory.decodeResource(resources, R.drawable.image)
            val compressFormat = Bitmap.CompressFormat.PNG
            val fos = FileOutputStream(file)
            bitmap.compress(compressFormat, 100, fos)
            fos.closeQuietly()
        }

        btnPickFile.setOnClickListener {
            pickFile()
        }

        btnGetUriFromFile.setOnClickListener {
            val dir = File(getExternalFilesDir(Environment.DIRECTORY_DCIM),"AndroidDeveloper")
            val file = File(dir, "image.png")
            val authority = "com.hewking.develop.fileprovider"
            val uri = FileProvider.getUriForFile(this@FileCompatActivity, authority, file)

            Logger.debug(TAG, "filePath:${file.path}", uri.toString())

            val file2 = File(cacheDir, "photo.png")
            Logger.debug(
                TAG,
                "2 filePath:${file2.path}",
                FileProvider.getUriForFile(this@FileCompatActivity, authority, file2).toString()
            )

            val file3 = File(getExternalFilesDir(Environment.DIRECTORY_MOVIES), "photo.png")
            Logger.debug(
                TAG,
                "3 filePath:${file3.path}",
                FileProvider.getUriForFile(this@FileCompatActivity, authority, file3).toString()
            )

            val file4 = File(getExternalFilesDir("every"), "test_every.png")
            Logger.debug(
                TAG,
                "4 filePath:${file4.path}",
                FileProvider.getUriForFile(this@FileCompatActivity, authority, file4).toString()
            )

            val file5 = File(getExternalFilesDir("files"), "test_files.png")
            Logger.debug(
                TAG,
                "5 filePath:${file5.path}",
                FileProvider.getUriForFile(this@FileCompatActivity, authority, file5).toString()
            )


        }

    }

    private fun showMediaListDir(){
//        MediaStore.Files
        Environment.getExternalStorageState()
    }

    /**
     * SAF 访问文件
     */
    private fun pickFile(){
        //通过系统的文件浏览器选择一个文件

        //通过系统的文件浏览器选择一个文件
        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT)
        //筛选，只显示可以“打开”的结果，如文件(而不是联系人或时区列表)
        //筛选，只显示可以“打开”的结果，如文件(而不是联系人或时区列表)
        intent.addCategory(Intent.CATEGORY_OPENABLE)
        //过滤只显示图像类型文件
        intent.type = "image/*"
        startActivityForResult(intent, REQUEST_CODE_FOR_SINGLE_FILE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, resultData: Intent?) {
        super.onActivityResult(requestCode, resultCode, resultData)
        if (requestCode === REQUEST_CODE_FOR_SINGLE_FILE && resultCode === Activity.RESULT_OK) {
            var uri: Uri? = null
            if (resultData != null) {
                // 获取选择文件Uri
                uri = resultData.getData()
                // 获取图片信息
                val cursor: Cursor? = this.contentResolver
                    .query(uri!!, IMAGE_PROJECTION, null, null, null, null)
                if (cursor != null && cursor.moveToFirst()) {
                    val displayName: String =
                        cursor.getString(cursor.getColumnIndexOrThrow(IMAGE_PROJECTION.get(0)))
                    val size: String =
                        cursor.getString(cursor.getColumnIndexOrThrow(IMAGE_PROJECTION.get(1)))
                    Log.i(TAG, "Uri: " + uri.toString())
                    Log.i(TAG, "Name: $displayName")
                    Log.i(TAG, "Size: $size")
                }
                cursor?.close()
            }
        }
    }

    fun createFile() {
        val intent =
            Intent(Intent.ACTION_CREATE_DOCUMENT)
        intent.addCategory(Intent.CATEGORY_OPENABLE)
        // 文件类型
        intent.type = "text/plain"
        // 文件名称
        intent.putExtra(
            Intent.EXTRA_TITLE,
            System.currentTimeMillis().toString() + ".txt"
        )
        startActivityForResult(intent, WRITE_REQUEST_CODE)
    }



    companion object{
        const val TAG = "FileCompatActivity"
        const val REQUEST_CODE_FOR_SINGLE_FILE = 10000
        const val WRITE_REQUEST_CODE = 10002
    }

}