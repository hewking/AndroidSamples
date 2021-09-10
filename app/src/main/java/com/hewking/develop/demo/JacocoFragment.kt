package com.hewking.develop.demo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.hewking.develop.R
import com.hewking.develop.databinding.FragmentJacocoBinding
import com.hewking.develop.test.JacocoHelper
import com.hewking.develop.util.toast

/**
 *@Description:
 *@Author: jianhao
 *@Date:   2021-09-10 16:43
 *@License: Copyright Since 2020 Hive Box Technology. All rights reserved.
 *@Notice: This content is limited to the internal circulation of
 *  Hive Box, and it is prohibited to leak or used for other commercial purposes.
 */
class JacocoFragment: Fragment() {

    private lateinit var binding: FragmentJacocoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentJacocoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val listener = View.OnClickListener {
            when(it.id) {
                R.id.btn1 -> toast(requireContext(), "测试1")
                R.id.btn2 -> toast(requireContext(), "测试2")
                R.id.btn3 -> toast(requireContext(), "测试3")
                R.id.btn4 -> toast(requireContext(), "测试4")
            }
        }

        binding.btn1.setOnClickListener(listener)
        binding.btn2.setOnClickListener(listener)
        binding.btn3.setOnClickListener(listener)
        binding.btn4.setOnClickListener(listener)

        binding.btnReport.setOnClickListener {
            JacocoHelper.generateEcFile(requireContext(), true)
        }

    }

}