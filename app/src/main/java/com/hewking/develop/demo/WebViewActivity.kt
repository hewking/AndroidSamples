package com.hewking.develop.demo

import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.annotation.RequiresApi
import com.hewking.develop.R
import com.hewking.develop.base.BaseActivity
import kotlinx.android.synthetic.main.activity_webview.*

/**
 * @author: jianhao
 * @create: 2020/8/19
 * @description:
 */
class WebViewActivity : BaseActivity(){

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_webview)

//        webview.loadUrl("https://h5-mall-sit2.fcbox.com/index.html?show=detail#/index/58200002")
        webview.loadUrl("https://juejin.im/post/6859784103621820429")
        webview.setOnScrollChangeListener { v, scrollX, scrollY, oldScrollX, oldScrollY ->
            Log.d("WebViewActivity","scrollX:$scrollX scrollY$scrollY oldScrollX:$oldScrollX oldScrollY$oldScrollY")
        }
    }

}