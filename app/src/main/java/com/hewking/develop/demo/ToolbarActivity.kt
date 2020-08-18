package com.hewking.develop.demo

import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.constraintlayout.widget.ConstraintLayout
import com.hewking.develop.R
import com.hewking.develop.base.BaseActivity
import com.hewking.develop.util.TransparentToolbarManager
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
//        setSupportActionBar(toolbar)
//        supportActionBar?.run {
//            setDisplayShowTitleEnabled(false)
//            setHomeButtonEnabled(true)
//            setDisplayHomeAsUpEnabled(true)
//        } ?: kotlin.run {
//
//        }

//        changeToolbar()
//        toolbar.background.alpha = 0
        webview.loadUrl("https://h5-mall-sit2.fcbox.com/index.html?show=detail#/index/20700188")

        val toolbarManager = TransparentToolbarManager(toolbar)
        toolbarManager.setToolbarAlpha(0)
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

}