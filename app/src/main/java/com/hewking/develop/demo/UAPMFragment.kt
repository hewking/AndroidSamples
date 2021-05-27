package com.hewking.develop.demo

import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import com.fcbox.screw.view.click
import com.umeng.umcrash.UMCrash


/**
 * @Description: (用一句话描述该文件做什么)
 * @Author: jianhao
 * @Date:   2021/5/27 17:20
 * @License: Copyright Since 2020 Hive Box Technology. All rights reserved.
 * @Notice: This content is limited to the internal circulation of Hive Box, 
 * and it is prohibited to leak or used for other commercial purposes.
 */
class UAPMFragment: Fragment(){

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val rootView = LinearLayout(requireActivity()).apply {
            layoutParams = ViewGroup.LayoutParams(-1, -1)
            orientation = LinearLayout.VERTICAL
            setupItems().invoke(this)
        }

        return rootView
    }

    private fun setupItems(): LinearLayout.() -> Unit {
        return {
            addView(Button(requireContext()).apply {
                layoutParams = LinearLayout.LayoutParams(-2, -2).apply {
                    gravity = Gravity.CENTER_HORIZONTAL
                }
                text = "自定义异常"
                isAllCaps = true
                click {
                    testCustomException()
                }
            })
        }
    }

    private fun testCustomException() {
        try {
            // 抛出异常的代码
            val count = 1 / 0
        } catch (e: Exception) {
            UMCrash.generateCustomLog(e, "UmengException")
            e.printStackTrace()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

}