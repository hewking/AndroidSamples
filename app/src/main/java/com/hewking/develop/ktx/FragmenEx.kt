package com.hewking.develop.ktx

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity

/**
 * @author: jianhao
 * @create: 2020/8/18
 * @description:
 */

/**
 * 显示fragment并根据tag隐藏正在显示的fragment
 * 创建或从缓存中查找的过程已经在本方法完成
 * 用于自行管理fragment的场景
 * @param tag 新fragment标签
 * @param holderId fragment占位视图的id
 * @param fragmentCreator fragment构造器
 * @param oldTag 正在显示的fragment的标签,如无则空
 */
fun Fragment.addOrReplaceFragment(tag: String, holderId: Int,
                                          fragmentCreator: () -> androidx.fragment.app.Fragment, oldTag: String? = null) {
    val fragmentTransaction = requireFragmentManager().beginTransaction()
    val cachedFragment = requireFragmentManager().findFragmentByTag(tag)
    val oldFragment = requireFragmentManager().findFragmentByTag(oldTag)
    if (oldFragment != null) {
        fragmentTransaction.hide(oldFragment)
    }
    if (cachedFragment == null) { // create
        fragmentTransaction.add(holderId, fragmentCreator.invoke(), tag)
    } else {
        fragmentTransaction.replace(holderId,cachedFragment)
    }
//    fragmentTransaction.addToBackStack(tag)
    fragmentTransaction.commitNowAllowingStateLoss()
}