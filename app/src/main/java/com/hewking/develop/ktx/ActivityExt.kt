package com.hewking.develop.ktx

import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.replace

/**
 * @author: jianhao
 * @create: 2020/7/15
 * @description: 定义一些Activity中的拓展方法
 */

fun FragmentActivity.addOrShowFragment(
    @IdRes containerId: Int,
    fragment: Fragment,
    tag: String = fragment::class.java.simpleName
) {
    val transition = supportFragmentManager.beginTransaction()
    val oldFragment = supportFragmentManager.findFragmentById(containerId)
    val cache = supportFragmentManager.findFragmentByTag(tag)
    oldFragment?.run {
        transition.hide(this)
    }
    cache?.run {
        transition.show(this)
    } ?: kotlin.run {
        transition.add(containerId, fragment, tag)
    }
    transition.addToBackStack(tag)
    transition.commitAllowingStateLoss()
}
