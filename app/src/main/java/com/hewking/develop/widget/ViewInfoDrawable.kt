package com.hewking.develop.widget

import android.graphics.*
import android.graphics.drawable.Drawable
import androidx.core.graphics.ColorUtils

/**
 * @author: jianhao
 * @create: 2020/9/10
 * @description: 对View tree 显示边界范围
 */
class ViewInfoDrawable : Drawable() {

    private val mPaint by lazy {
        Paint().apply {
            style = Paint.Style.STROKE
            isAntiAlias = true
            strokeCap = Paint.Cap.BUTT
            pathEffect = DashPathEffect(floatArrayOf(4f,4f),0f)
            color = ColorUtils.setAlphaComponent(Color.RED, 0x91)
        }
    }

    var viewInfos = mutableListOf<ViewInfo>()
        set(value) {
            field.clear()
            field.addAll(value)
            invalidateSelf()
        }

    override fun draw(canvas: Canvas) {
        viewInfos.forEach {
            canvas.drawRect(
                it.left.toFloat(),
                it.top.toFloat(),
                it.left.toFloat() + it.width,
                it.top.toFloat() + it.height
                , mPaint
            )
        }
    }

    override fun setAlpha(alpha: Int) {
    }

    override fun getOpacity(): Int {
        return PixelFormat.OPAQUE
    }

    override fun setColorFilter(colorFilter: ColorFilter?) {
    }

    data class ViewInfo(
        val left: Int = 0,
        val top: Int = 0,
        val width: Int = 0,
        val height: Int = 0
    )


}