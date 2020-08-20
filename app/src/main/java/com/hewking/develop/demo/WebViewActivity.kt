package com.hewking.develop.demo

import android.graphics.Bitmap
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.webkit.*
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

    companion object{
        const val TAG = "WebViewActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_webview)

//        webview.loadUrl("https://h5-mall-sit2.fcbox.com/index.html?show=detail#/index/58200002")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            webview.setOnScrollChangeListener { v, scrollX, scrollY, oldScrollX, oldScrollY ->
                Log.d("WebViewActivity","scrollX:$scrollX scrollY$scrollY oldScrollX:$oldScrollX oldScrollY$oldScrollY")
            }
        }
        webview.webViewClient = webviewClient
        webview.webChromeClient = webChromeClient

        webview.settings.cacheMode = WebSettings.LOAD_NO_CACHE

        webview.loadUrl("https://juejin.im/post/6859784103621820429")

    }

    private val webviewClient = object : WebViewClient(){
        override fun shouldOverrideUrlLoading(
            view: WebView?,
            request: WebResourceRequest?
        ): Boolean {
            Log.d(TAG,"shouldOverrideUrlLoading request url:${request?.url}" +
                    " ")
            return super.shouldOverrideUrlLoading(view, request)
        }

        override fun onPageFinished(view: WebView?, url: String?) {
            super.onPageFinished(view, url)
            Log.d(TAG,"onPageFinished url:${url}")
        }

        override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
            super.onPageStarted(view, url, favicon)
            Log.d(TAG,"onPageStarted url:$url")
        }
    }

    private val webChromeClient = object: WebChromeClient(){
        override fun onProgressChanged(view: WebView?, newProgress: Int) {
            super.onProgressChanged(view, newProgress)
            Log.d(TAG,"onProgressChanged progress:$newProgress")
        }

    }

    override fun onBackPressed() {
        if (webview.canGoBack()) {
            webview.goBack()
            return
        }
        super.onBackPressed()
    }

}