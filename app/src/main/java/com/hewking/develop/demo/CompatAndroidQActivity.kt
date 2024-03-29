package com.hewking.develop.demo

import android.Manifest
import android.app.Activity
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.provider.Settings
import android.telephony.TelephonyManager
import android.util.Log
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.hewking.develop.R
import com.hewking.develop.databinding.ActivityCompatAndroidqBinding
import com.hewking.develop.util.DeviceIdUtils
import com.hewking.develop.util.Logger
import com.hewking.develop.util.NotchUtil
import com.hewking.develop.util.toast
import com.hewking.develop.vm.MainViewModel
import kotlinx.android.synthetic.main.activity_compat_androidq.*
import java.io.BufferedInputStream
import java.io.BufferedOutputStream
import java.io.File
import java.io.FileOutputStream
import java.net.HttpURLConnection
import java.net.URL
import kotlin.concurrent.thread

/**
 * @Description: (用一句话描述该文件做什么)
 * @Author: jianhao
 * @Date:   2020/9/14 17:33
 * @License: Copyright Since 2020 Hive Box Technology. All rights reserved.
 * @Notice: This content is limited to the internal circulation of Hive Box, and it is prohibited to leak or used for other commercial purposes.
 */

const val PICK_FILE = 1

class CompatAndroidQActivity : AppCompatActivity() {

    private val viewModel by viewModels<MainViewModel>()

    private lateinit var binding: ActivityCompatAndroidqBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityCompatAndroidqBinding.inflate(layoutInflater)
        setContentView(binding.root)

        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        initNotchState()

        val permissionsToRequire = ArrayList<String>()
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.READ_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            permissionsToRequire.add(Manifest.permission.READ_EXTERNAL_STORAGE)
        }
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            permissionsToRequire.add(Manifest.permission.WRITE_EXTERNAL_STORAGE)
        }
        if (permissionsToRequire.isNotEmpty()) {
            ActivityCompat.requestPermissions(this, permissionsToRequire.toTypedArray(), 0)
        }
        binding.browseAlbum.setOnClickListener {
            val intent = Intent(this, BrowseAlbumActivity::class.java)
            startActivity(intent)
        }
        binding.addImageToAlbum.setOnClickListener {
            val bitmap = BitmapFactory.decodeResource(resources, R.drawable.image)
            val displayName = "${System.currentTimeMillis()}.jpg"
            val mimeType = "image/jpeg"
            val compressFormat = Bitmap.CompressFormat.JPEG
            addBitmapToAlbum(bitmap, displayName, mimeType, compressFormat)
        }
        binding.downloadFile.setOnClickListener {
            val fileUrl = "http://guolin.tech/android.txt"
            val fileName = "android.txt"
            downloadFile(fileUrl, fileName)

        }
        binding.pickFile.setOnClickListener {
            pickFileAndCopyUriToExternalFilesDir()
        }

        binding.btnGetIMEI.setOnClickListener {
            val androidId = getIMEI()
            val info = DeviceIdUtils.systembuildInfo();
            toast("androidID:$androidId")
            Logger.debug("androidID", "test", "androidID:$androidId")
            Logger.debug("androidID", "test", "systemInfo:$info")
        }

        binding.testFileApi.setOnClickListener {
            startActivity(Intent(this@CompatAndroidQActivity, FileCompatActivity::class.java))
            finish()
            viewModel.doLongTask()

        }

        setupObservers()

    }

    private fun setupObservers() {
        viewModel.triggerLiveData.observe(this, {
            Log.d("CompatAndroidQActivity", "trigger")
        })
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 0) {
            for (result in grantResults) {
                if (result != PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "You must allow all the permissions.", Toast.LENGTH_SHORT)
                        .show()
                    finish()
                }
            }
        }
    }

    private fun addBitmapToAlbum(
        bitmap: Bitmap,
        displayName: String,
        mimeType: String,
        compressFormat: Bitmap.CompressFormat
    ) {
        val values = ContentValues()
        values.put(MediaStore.MediaColumns.DISPLAY_NAME, displayName)
        values.put(MediaStore.MediaColumns.MIME_TYPE, mimeType)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            values.put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_DCIM)
        } else {
            values.put(
                MediaStore.MediaColumns.DATA,
                "${Environment.getExternalStorageDirectory().path}/${Environment.DIRECTORY_DCIM}/$displayName"
            )
        }
        val uri = contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values)
        if (uri != null) {
            val outputStream = contentResolver.openOutputStream(uri)
            if (outputStream != null) {
                bitmap.compress(compressFormat, 100, outputStream)
                outputStream.close()
                Toast.makeText(this, "Add bitmap to album succeeded.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun downloadFile(fileUrl: String, fileName: String) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) {
            Toast.makeText(
                this,
                "You must use device running Android 10 or higher",
                Toast.LENGTH_SHORT
            ).show()
            return
        }
        thread {
            try {
                val url = URL(fileUrl)
                val connection = url.openConnection() as HttpURLConnection
                connection.requestMethod = "GET"
                connection.connectTimeout = 8000
                connection.readTimeout = 8000
                val inputStream = connection.inputStream
                val bis = BufferedInputStream(inputStream)
                val values = ContentValues()
                values.put(MediaStore.MediaColumns.DISPLAY_NAME, fileName)
                values.put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_DOWNLOADS)
                val uri = contentResolver.insert(MediaStore.Downloads.EXTERNAL_CONTENT_URI, values)
                if (uri != null) {
                    val outputStream = contentResolver.openOutputStream(uri)
                    if (outputStream != null) {
                        val bos = BufferedOutputStream(outputStream)
                        val buffer = ByteArray(1024)
                        var bytes = bis.read(buffer)
                        while (bytes >= 0) {
                            bos.write(buffer, 0, bytes)
                            bos.flush()
                            bytes = bis.read(buffer)
                        }
                        bos.close()
                        runOnUiThread {
                            Toast.makeText(
                                this,
                                "$fileName is in Download directory now.",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }
                bis.close()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private fun pickFileAndCopyUriToExternalFilesDir() {
        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT)
        intent.addCategory(Intent.CATEGORY_OPENABLE)
        intent.type = "*/*"
        startActivityForResult(intent, PICK_FILE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            PICK_FILE -> {
                if (resultCode == Activity.RESULT_OK && data != null) {
                    val uri = data.data
                    if (uri != null) {
                        val fileName = getFileNameByUri(uri)
                        copyUriToExternalFilesDir(uri, fileName)
                    }
                }
            }
        }
    }

    private fun getFileNameByUri(uri: Uri): String {
        var fileName = System.currentTimeMillis().toString()
        val cursor = contentResolver.query(uri, null, null, null, null)
        if (cursor != null && cursor.count > 0) {
            cursor.moveToFirst()
            fileName =
                cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DISPLAY_NAME))
            cursor.close()
        }
        return fileName
    }

    private fun copyUriToExternalFilesDir(uri: Uri, fileName: String) {
        thread {
            val inputStream = contentResolver.openInputStream(uri)
            val tempDir = getExternalFilesDir("temp")
            if (inputStream != null && tempDir != null) {
                val file = File("$tempDir/$fileName")
                val fos = FileOutputStream(file)
                val bis = BufferedInputStream(inputStream)
                val bos = BufferedOutputStream(fos)
                val byteArray = ByteArray(1024)
                var bytes = bis.read(byteArray)
                while (bytes > 0) {
                    bos.write(byteArray, 0, bytes)
                    bos.flush()
                    bytes = bis.read(byteArray)
                }
                bos.close()
                fos.close()
                runOnUiThread {
                    Toast.makeText(this, "Copy file into $tempDir succeeded.", Toast.LENGTH_LONG)
                        .show()
                }
            }
        }
    }

    private fun getIMEI(): String? {
        val manager = getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager

        /**
         * Build.getSerial();
        TelephonyManager.getImei();
        TelephonyManager.getMeid()
        TelephonyManager.getDeviceId();
        TelephonyManager.getSubscriberId();
        TelephonyManager.getSimSerialNumber();
         */
//         manager.imei
        var androidId: String? =
            Settings.Secure.getString(this.contentResolver, Settings.Secure.ANDROID_ID)

        return androidId
    }

    val XIAOMI = "XIAOMI"
    val HUAWEI = "HUAWEI"
    val OPPO = "OPPO"
    val VIVO = "VIVO"

    private fun initNotchState() {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.O_MR1) {
            val window = window
            val lp = window.attributes
            lp.layoutInDisplayCutoutMode =
                WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES
            window.attributes = lp
            window.attributes = lp
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O
            && Build.VERSION.SDK_INT <= Build.VERSION_CODES.O_MR1
        ) {
            val brand: String = android.os.Build.BRAND
            when (brand) {
                XIAOMI -> if (NotchUtil.hasNotchAtXiaoMi()) {
                    NotchUtil.setNotchStateAtXiaoMi(this)
                }
                HUAWEI -> if (NotchUtil.hasNotchAtHuaWei(this)) {
                }
                OPPO -> if (NotchUtil.hasNotchAtOPPO(this)) {
                }
                VIVO -> if (NotchUtil.hasNotchAtVivo(this)) {
                }
                else -> {
                }
            }
        } else {
        }
    }

}