package com.hewking.develop

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import com.hewking.develop.demo.DemoListFragment
import com.hewking.develop.service.CountDownService

class MainActivity : AppCompatActivity() {

    private var binder: CountDownService.CountDownBinder? = null
    private var bound = false

    private val connection = object : ServiceConnection {
        override fun onServiceDisconnected(name: ComponentName?) {
            bound = false
        }

        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            binder = service as CountDownService.CountDownBinder?
            bound = true
            Log.d("MainActivity", "count:${binder?.getService()?.getCount()}")
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportFragmentManager.beginTransaction()
            .add(R.id.frameLayout, DemoListFragment(), "DemoListFragment")
            .commit()

    }

    override fun onStop() {
        super.onStop()
        unbindService(connection)
    }

    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount > 0) {
            supportFragmentManager.popBackStack()
        } else {
            super.onBackPressed()
        }
    }
}
