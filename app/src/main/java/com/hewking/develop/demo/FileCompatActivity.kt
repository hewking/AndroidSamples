package com.hewking.develop.demo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import androidx.core.os.EnvironmentCompat
import com.hewking.develop.R
import com.hewking.develop.util.Logger
import kotlinx.android.synthetic.main.activity_file_compat.*

class FileCompatActivity : AppCompatActivity() {
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

    }
}