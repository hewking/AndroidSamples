package com.hewking.develop.widget

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.view.ViewGroup
import androidx.core.view.children
import com.hewking.develop.R
import java.lang.IllegalArgumentException
import kotlin.math.max

/**
 * @author: jianhao
 * @create: 2020/8/18
 * @description: 可以调整布局顺序的Layout
 */
class FloatFrameLayout(val ctx: Context, attrs: AttributeSet) :
    ViewGroup(ctx, attrs) {

    var layoutMode = LayoutMode.Linear
        set(value) {
            field = value
            requestLayout()
        }

    init {
        val typeArray = ctx.obtainStyledAttributes(attrs, R.styleable.FloatFrameLayout)
        typeArray.recycle()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val wMode = MeasureSpec.getMode(widthMeasureSpec)
        val hMode = MeasureSpec.getMode(heightMeasureSpec)
        var wSize = MeasureSpec.getSize(widthMeasureSpec)
        var hSize = MeasureSpec.getSize(heightMeasureSpec)

        measureChildren(widthMeasureSpec, heightMeasureSpec)

        if (wMode != MeasureSpec.EXACTLY) {
            wSize = children.map { it.measuredWidth }.reduce { acc, i -> max(acc, i) }
        }

        if (hMode != MeasureSpec.EXACTLY) {
            if (layoutMode == LayoutMode.Float) {
                hSize = children.map { it.measuredHeight }.reduce { acc, i -> max(acc, i) }
            } else {
                hSize = children.map { it.measuredHeight }.reduce { acc, i -> acc + i }
            }
        }

        setMeasuredDimension(wSize, hSize)
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        if (layoutMode == LayoutMode.Float || layoutMode == LayoutMode.FloatReverse) {
            layoutFloat(left, top, right, bottom)
        } else {
            layoutLinear(left, top, right, bottom)
        }
    }

    private fun layoutFloat(left: Int, top: Int, right: Int, bottom: Int) {
        if (layoutMode == LayoutMode.Float) {
            children.forEach {
                it.layout(left, top, left + it.measuredWidth, top + it.measuredHeight)
            }
        } else {
            val count = childCount
            for (i in count - 1 downTo 0) {
                val child = getChildAt(i)
                Log.d("child",child::class.java.simpleName)
                child.layout(left,top,left + child.measuredWidth,top + child.measuredHeight)
            }

//            children.toMutableList().reversed().forEach {
//                it.layout(left, top, left + it.measuredWidth, top + it.measuredHeight)
//            }
        }
    }

    private fun layoutLinear(left: Int, top: Int, right: Int, bottom: Int) {
        var childTop = top
        children.forEach {
            it.layout(left, childTop, left + it.measuredWidth, childTop + it.measuredHeight)
            childTop += it.measuredHeight
        }
    }

    override fun addView(child: View?, index: Int, params: ViewGroup.LayoutParams?) {
        super.addView(child, index, params)
        if (childCount > 2) {
            throw IllegalArgumentException("不能超过两个child View!")
        }
    }

    override fun generateDefaultLayoutParams(): LayoutParams {
        return super.generateDefaultLayoutParams()
    }

    enum class LayoutMode {
        Linear, Float, FloatReverse
    }

}