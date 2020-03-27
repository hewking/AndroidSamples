package com.hewking.develop.demo.aidl

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import com.hewking.develop.IMusicManager

class MusicManager : Service() {

    companion object{
        private const val TAG = "MusicManager"
    }

    override fun onBind(intent: Intent?): IBinder? {
        Log.d(TAG,"MusicManager 服务已开启")
        return Stub()
    }

    private inner class Stub : IMusicManager.Stub() {
        override fun getCount(): Int {
           return 10
        }

    }

}