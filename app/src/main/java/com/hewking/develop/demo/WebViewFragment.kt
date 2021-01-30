package com.hewking.develop.demo

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import androidx.fragment.app.Fragment
import com.hewking.develop.databinding.FragmentWebviewBinding
import org.json.JSONArray
import org.json.JSONObject
import kotlin.random.Random


class WebViewFragment : Fragment() {

    private lateinit var binding: FragmentWebviewBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentWebviewBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            WebView.setWebContentsDebuggingEnabled(true);
        }

        binding.webview.apply {
            settings.javaScriptEnabled = true
            loadUrl("file:///android_asset/echart_demo.html")
        }

        binding.btnAdd.setOnClickListener {
            // [5, 20, 36, 10, 10, 20]
            val values = IntArray(6).also {
                it.fill(0)
            }.map {
                Random.nextInt(0,100)
            }

            setSeries(values.toIntArray())

        }

    }

    fun setSeries(values: IntArray){
        val jsonObject = JSONObject()
        val array = JSONArray().apply {
            values.forEach {
                put(it)
            }
        }
        jsonObject.put("datas",array)
        Log.d("setSeries", "json: ${jsonObject.toString()}")
        binding.webview.loadUrl("javascript:setSeriesForNative(" + jsonObject.toString() + ")")
//        binding.webview.loadUrl("javascript:changeSeries()")

//        binding.webview.evaluateJavascript("changeSeries()",{
//
//        })

    }

}