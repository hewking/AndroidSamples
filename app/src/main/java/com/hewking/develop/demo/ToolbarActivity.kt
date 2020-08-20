package com.hewking.develop.demo

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.SeekBar
import androidx.constraintlayout.widget.ConstraintLayout
import com.gyf.immersionbar.ImmersionBar
import com.hewking.develop.R
import com.hewking.develop.base.BaseActivity
import com.hewking.develop.util.StateBarUtils
import com.hewking.develop.util.StatusBarManager
import com.hewking.develop.util.TransparentToolbarManager
import com.hewking.develop.widget.FloatFrameLayout
import kotlinx.android.synthetic.main.fragment_toolbar.*

/**
 * @author: jianhao
 * @create: 2020/8/18
 * @description:
 */
class ToolbarActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.run {
            setDisplayShowTitleEnabled(false)
            setHomeButtonEnabled(true)
            setDisplayHomeAsUpEnabled(false)
        } ?: kotlin.run {

        }

//        changeToolbar()
//        toolbar.background.alpha = 0
//        webview.loadUrl("https://h5-mall-sit2.fcbox.com/index.html?show=detail#/index/20700188")
//        webview.loadUrl("https://h5-mall-sit2.fcbox.com/index.html")

        val toolbarManager = TransparentToolbarManager(toolbar)

        seekbar.max = 255

        seekbar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                toolbarManager.manageFadingToolbar(progress)
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
            }

        })

        toolbarManager.manageFadingToolbar(0)
        btn_change.setOnClickListener {
            if (cl_root.layoutMode == FloatFrameLayout.LayoutMode.FloatReverse) {
                cl_root.layoutMode = FloatFrameLayout.LayoutMode.Linear
            } else {
                cl_root.layoutMode = FloatFrameLayout.LayoutMode.FloatReverse
            }
        }

        StatusBarManager(this).create(false)
        StateBarUtils.paddingTopStateBarHeight(this,toolbar)
//        ImmersionBar.with(this).statusBarColorInt(Color.TRANSPARENT)
//            .fitsSystemWindows(true)
//            .init()
    }

    private fun changeToolbar() {
        webview.apply {
            val lp = layoutParams as ConstraintLayout.LayoutParams
            lp.topToBottom = ConstraintLayout.LayoutParams.UNSET
            lp.topToTop = ConstraintLayout.LayoutParams.PARENT_ID
            layoutParams = lp
        }
    }

    /**
     * 响应toolbar 里面的navigationIcon 点击事件
     */
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        Log.d("onOptionsItemSelected", item.toString())
        when (item.itemId) {
            android.R.id.home -> {
                val fragmentSize = supportFragmentManager.backStackEntryCount
                if (0 < fragmentSize) {
                    supportFragmentManager.popBackStack()
                } else {
                    onBackPressed()
                }
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun isImmersive(): Boolean {
        return true
    }

}