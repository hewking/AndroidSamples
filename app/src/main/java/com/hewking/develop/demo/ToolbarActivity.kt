package com.hewking.develop.demo

import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.SeekBar
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.graphics.ColorUtils
import com.hewking.develop.R
import com.hewking.develop.base.BaseActivity
import com.hewking.develop.util.GradientViewManager
import com.hewking.develop.util.StateBarUtils
import com.hewking.develop.util.StatusBarManager
import com.hewking.develop.util.TransparentViewManager
import com.hewking.develop.widget.FloatFrameLayout
import kotlinx.android.synthetic.main.fragment_toolbar.*

/**
 * @author: jianhao
 * @create: 2020/8/18
 * @description:
 */
class ToolbarActivity : BaseActivity() {

    companion object {
        const val TAG = "ToolbarActivity"
    }

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

        val toolbarManager =
            TransparentViewManager(toolbar)

        toolbarManager.manageFadingToolbar(0)

        seekbar.max = 255

        seekbar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                toolbarManager.manageFadingToolbar(progress)
                // change ivback background
                val color = ContextCompat.getColor(
                    this@ToolbarActivity,
                    R.color.white
                )
//                val alpha = (1 - progress.div(255f)).times(0x91)
//                val argb = ColorUtils.setAlphaComponent(color, alpha.toInt())
//                val grad = iv_back.background as GradientDrawable
//
//                val alphaMenu = (1 - progress.div(255f)).times(0x45) + 0x12
//                val argb2 = ColorUtils.setAlphaComponent(color, alphaMenu.toInt())
//                val grad2 = ll_menu.background as GradientDrawable
//
//                grad.setColor(argb)
//                grad2.setColor(argb2)

                val m1 = GradientViewManager(iv_back, color, { progress2 ->
                    (1 - progress2.div(255f)).times(0x91)
                })

                val m2 = GradientViewManager(ll_menu,color,{
                    (1 - it.div(255f)).times(0x45) + 0x12
                })

                m1.gradient(progress)
                m2.gradient(progress)

            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
            }

        })

        btn_change.setOnClickListener {
            if (cl_root.layoutMode == FloatFrameLayout.LayoutMode.FloatReverse) {
                cl_root.layoutMode = FloatFrameLayout.LayoutMode.Linear
            } else {
                cl_root.layoutMode = FloatFrameLayout.LayoutMode.FloatReverse
            }
        }

        StatusBarManager(this).create(false)
        StateBarUtils.paddingTopStateBarHeight(this, toolbar)
//        ImmersionBar.with(this).statusBarColorInt(Color.TRANSPARENT)
//            .fitsSystemWindows(true)
//            .init()

        Log.d(TAG, "drawable class : ${iv_back.background.javaClass.simpleName}")

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