package com.hewking.develop.demo

import android.os.Bundle
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.hewking.develop.R
import kotlinx.android.synthetic.main.res_demo_fragment.*

/**
 * @author: jianhao
 * @create: 2020/7/21
 * @description: Android Switch 自定义相关demo
 */
class SwitchDemoFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.switch_demo_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

}