package com.hewking.develop.util

import android.app.Activity
import android.graphics.Color
import android.os.Build
import android.view.View
import android.view.WindowManager
import com.hewking.develop.R
import com.readystatesoftware.systembartint.SystemBarTintManager

/**
 * @author: salmonzhg
 * @create: 2020/1/2
 * @description:
 */
class StatusBarManager(private val activity: Activity) {

    fun create(isShowFullScreen: Boolean) {
        if (isShowFullScreen) {
            activity.window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN) //不显示系统的标题栏
        } else {
            if (activity.isImmersive) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    val window = this.activity.window
                    window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
                    window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            or View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR)
                    window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
                    window.statusBarColor = Color.TRANSPARENT
                }
            } else {
                //android 4.4设置statubar的颜色
                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M && Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//                    val window = this.activity.window
//                    window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
//                    activity.findViewById<View>(R.id.content_container)?.fitsSystemWindows = true
//                    val tintManager = SystemBarTintManager(activity)
//                    tintManager.isStatusBarTintEnabled = true
//                    tintManager.setStatusBarTintResource(R.color.black_transparent)
                }
            }
        }
    }

    fun immersive(){
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
            return
        }
        // 4.4 - 5.0
        if (Build.VERSION.SDK_INT in Build.VERSION_CODES.KITKAT until Build.VERSION_CODES.LOLLIPOP) {
            // sit1: 背景是一张图片的情况
            activity.window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        } else if (Build.VERSION.SDK_INT in Build.VERSION_CODES.LOLLIPOP until Build.VERSION_CODES.M) {

        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

        }
    }

}