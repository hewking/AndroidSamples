package com.hewking.develop.demo

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.graphics.Color
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.graphics.ColorUtils
import androidx.fragment.app.Fragment
import com.hewking.develop.R
import com.hewking.develop.demo.aidl.MusicManager
import kotlinx.android.synthetic.main.activity_main.view.*
import kotlinx.android.synthetic.main.test_fragment.*

class TestDemoFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.test_fragment,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btn_change.setOnClickListener {

            val color = ColorUtils.setAlphaComponent(iv_text.currentTextColor,0x80)
            iv_text.setTextColor(color)
        }

        btn_change2.setOnClickListener {
            ll_mask.showMask = true
        }

        iv_text
    }

}