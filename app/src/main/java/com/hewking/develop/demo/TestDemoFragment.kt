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
import com.hewking.develop.databinding.TestFragmentBinding
import com.hewking.develop.demo.aidl.MusicManager
import com.hewking.develop.demo.dialog.CustomDialog
import com.hewking.develop.ktx.toDp
import com.hewking.develop.ktx.toDpi
import kotlinx.android.synthetic.main.activity_main.view.*
import kotlinx.android.synthetic.main.test_fragment.*

class TestDemoFragment : Fragment() {

    private lateinit var binding: TestFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = TestFragmentBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnChange.setOnClickListener {

            val color = ColorUtils.setAlphaComponent(iv_text.currentTextColor,0x80)
            iv_text.setTextColor(color)
        }

        binding.btnChange2.setOnClickListener {
            ll_mask.showMask = true
        }

        binding.listItem.run {
            tvTitle.text = "小美女"
            tvSubtitle.text = "家里了"
        }

        binding.btnShowDialog.setOnClickListener {
            CustomDialog.Builder(requireContext())
                .setTitle("标题咋样")
                .setMessage("消息里面的呢从有哪些呢搞起来")
                .setPositiveButton("确定") { dialog, which ->
                    dialog.dismiss()

                }.setNegativeButton("取消",{
                    dialog, which ->
                    dialog.dismiss()
                })

                .create().apply {
                    show()
                    val params = window?.attributes
                    params?.apply {
//                        width = 270.toDpi()
                    }

                    println("dialog height:${params?.height} width:${params?.width}")
//                    window?.setLayout(270.toDpi(),-2)
                }
        }

    }

}