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
 * @description: Android res 资源相关的demo
 */
class ResDemoFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.res_demo_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tv_text.text = Html.fromHtml(getString(R.string.box_book_desc,"小明"))
    }

}