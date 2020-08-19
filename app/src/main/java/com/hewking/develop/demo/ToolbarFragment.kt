package com.hewking.develop.demo

import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.FrameLayout
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import com.hewking.develop.R

/**
 * @author: jianhao
 * @create: 2020/8/17
 * @description:
 */
class ToolbarFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return LinearLayout(requireContext()).apply {
            orientation = LinearLayout.VERTICAL
            layoutParams = LinearLayout.LayoutParams(-1,-1).apply {
                gravity = Gravity.CENTER
            }
            addView(Button(requireContext()).apply {
                layoutParams = LinearLayout.LayoutParams(-2,-2).apply {
                    gravity = Gravity.CENTER
                }
                text = "test toolbar"
                setOnClickListener {
                    startActivity(Intent(requireContext(),ToolbarActivity::class.java))
                }
            })

            addView(Button(requireContext()).apply {
                layoutParams = LinearLayout.LayoutParams(-2,-2)
                text = "test toolbar immersive"
                setOnClickListener {
                    startActivity(Intent(requireContext(),WebViewActivity::class.java))
                }
            })
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }


}