package com.hewking.client

import android.app.Activity
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.hewking.client.databinding.ActivityMainBinding
import com.hewking.develop.IMusicManager

class MainActivity : Activity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.btnStart.setOnClickListener {
            startService()
        }

    }

    private fun startService() {
        Log.d("client","startService")
        Intent().also {
            it.action = "start_music_manager"
            it.setPackage("com.hewking.develop")
            bindService(it,connection, Context.BIND_AUTO_CREATE)
        }
    }

    private val connection  = object : ServiceConnection{
        override fun onServiceDisconnected(name: ComponentName?) {

        }

        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            Log.d("client","onServiceConnected")
            service?:return
            val proxy = IMusicManager.Stub.asInterface(service)
            binding.tvCount.text = "有${proxy.count} 首歌"
        }

    }
}
