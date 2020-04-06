package com.hewking.develop.app

import android.app.Application
import android.content.Context
import androidx.multidex.MultiDex

class DemoApplication : Application(){

    override fun onCreate() {
        super.onCreate()
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(base)
    }

}