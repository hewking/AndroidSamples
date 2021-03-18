package com.hewking.develop.app

import android.app.Application
import android.content.Context
import androidx.lifecycle.ProcessLifecycleOwner
import androidx.multidex.MultiDex
import com.didichuxing.doraemonkit.DoraemonKit
import com.hewking.develop.db.GreenDao

class DemoApplication : Application(){

    override fun onCreate() {
        super.onCreate()
//        DoraemonKit.install(this)
        ProcessLifecycleOwner.get().lifecycle.addObserver(ApplicationLifecycleObserver())

        GreenDao.get().init(this)
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(base)
    }

}