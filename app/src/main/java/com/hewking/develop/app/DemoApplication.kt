package com.hewking.develop.app

import android.app.Application
import android.content.Context
import androidx.lifecycle.ProcessLifecycleOwner
import androidx.multidex.MultiDex
import com.didichuxing.doraemonkit.DoraemonKit

class DemoApplication : Application(){

    override fun onCreate() {
        super.onCreate()
//        DoraemonKit.install(this)
        ProcessLifecycleOwner.get().lifecycle.addObserver(ApplicationLifecycleObserver())
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(base)
    }

}