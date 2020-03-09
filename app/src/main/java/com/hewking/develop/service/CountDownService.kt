package com.hewking.develop.service

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import android.util.Log

/**
 *  一个测试service的demo。用于倒计数
 * **/
class CountDownService : Service() {

    companion object {
        private const val TAG = "CountDownService"
    }

    private var count = 10

    private val localBinder = CountDownBinder()

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.d(TAG,"onStartCommand")
        Worker().start()
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onBind(intent: Intent?): IBinder? {
        return localBinder
    }

    override fun onUnbind(intent: Intent?): Boolean {
        return super.onUnbind(intent)
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG,"onDestroy")
    }

    fun getCount() = this.count

    private inner class Worker : Thread() {
        override fun run() {
            super.run()
            doWork()
            stopSelf()
        }

        fun doWork() {
            for (i in 0 until 10) {
                sleep(1000L)
                count--
                Log.d(TAG,"count:$count")
            }
        }
    }

    inner class CountDownBinder : Binder() {

        fun getService() = this@CountDownService

    }

}