package com.hewking.develop.service

import android.app.Service
import android.content.Intent
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

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onBind(intent: Intent?): IBinder? {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onUnbind(intent: Intent?): Boolean {
        return super.onUnbind(intent)
    }

    override fun onDestroy() {
        super.onDestroy()
    }

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

}