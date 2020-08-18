package com.hewking.develop.demo

import android.os.Bundle
import com.hewking.develop.R
import com.hewking.develop.base.BaseActivity
import kotlinx.android.synthetic.main.fragment_toolbar.*

/**
 * @author: jianhao
 * @create: 2020/8/18
 * @description:
 */
class ToolbarActivity: BaseActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_toolbar)

        supportActionBar?.run {
            setSupportActionBar(toolbar)
        }?: kotlin.run {

        }
    }

}