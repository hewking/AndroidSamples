package com.hewking.develop.util

import android.graphics.drawable.GradientDrawable
import android.view.View
import androidx.annotation.ColorInt
import androidx.core.graphics.ColorUtils

/**
 * @author: jianhao
 * @create: 2020/9/1
 * @description:
 */
class GradientViewManager(
    val view: View,
    @ColorInt val color: Int,
    private val action: (progress: Int) -> Float,
    var min: Int = MIN,
    var max: Int = MAX
) {

    companion object {
        const val MAX = 255
        const val MIN = 0
    }

    private var gradientBackground: GradientDrawable? = null

    init {
        if (view.background is GradientDrawable) {
            gradientBackground = view.background as GradientDrawable
        }
    }

    fun gradient(progress: Int) {
        gradientBackground ?: return
        if (progress in min..max) {
            setAlpha(progress)
        } else if (progress > max) {
            setAlpha(max)
        }

    }

    private fun setAlpha(progress: Int){
        val alpha = action.invoke(progress)
        val argb = ColorUtils.setAlphaComponent(color, alpha.toInt())
        gradientBackground?.setColor(argb)
    }


}