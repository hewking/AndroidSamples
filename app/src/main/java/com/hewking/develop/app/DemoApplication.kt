package com.hewking.develop.app

import android.app.Application
import android.content.Context
import androidx.lifecycle.ProcessLifecycleOwner
import androidx.multidex.MultiDex
import com.didichuxing.doraemonkit.DoraemonKit
import com.hewking.develop.db.GreenDao
import com.tencent.bugly.crashreport.CrashReport
import com.umeng.commonsdk.UMConfigure

class DemoApplication : Application(){

    override fun onCreate() {
        super.onCreate()
//        DoraemonKit.install(this)
        ProcessLifecycleOwner.get().lifecycle.addObserver(ApplicationLifecycleObserver())

        instance = this

        GreenDao.get().init(this)

        UMConfigure.init(this, "60af5b916c421a3d97cf5988", "Umeng", UMConfigure.DEVICE_TYPE_PHONE, "");
        UMConfigure.setLogEnabled(true)

        CrashReport.initCrashReport(this, "69ce407acd", true);

    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(base)
    }

    companion object {

        lateinit var instance: Application

        fun get(): Application{
            return instance
        }

    }

}