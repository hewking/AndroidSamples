package com.hewking.develop

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import com.hewking.develop.service.CountDownService
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private var binder : CountDownService.CountDownBinder?= null
    private var bound = false

    private val connection = object : ServiceConnection{
        override fun onServiceDisconnected(name: ComponentName?) {
            bound = false
        }

        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            binder = service as CountDownService.CountDownBinder?
            bound = true
            Log.d("MainActivity","count:${binder?.getService()?.getCount()}")
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        btn_start.setOnClickListener {
            startService(Intent(this@MainActivity,CountDownService::class.java))
        }

        btn_bind.setOnClickListener {
            Intent(this@MainActivity,CountDownService::class.java).also {
                bindService(it,connection,Context.BIND_AUTO_CREATE)
            }
        }

    }

    override fun onStop() {
        super.onStop()
        unbindService(connection)
    }
}
