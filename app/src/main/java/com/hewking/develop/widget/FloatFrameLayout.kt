package com.hewking.develop.widget

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.core.view.children
import com.hewking.develop.R
import java.lang.IllegalArgumentException
import kotlin.math.max
import kotlin.math.min
import kotlin.math.sign

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
//            invalidate()
        }

    init {
        val typeArray = ctx.obtainStyledAttributes(attrs, R.styleable.FloatFrameLayout)
        typeArray.recycle()
        isChildrenDrawingOrderEnabled = true
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val wMode = MeasureSpec.getMode(widthMeasureSpec)
        val hMode = MeasureSpec.getMode(heightMeasureSpec)
        var wSize = MeasureSpec.getSize(widthMeasureSpec)
        var hSize = MeasureSpec.getSize(heightMeasureSpec)

//        measureChildren(widthMeasureSpec, heightMeasureSpec)

        var totalHeight = 0
        for (i in 0 until childCount) {
            val child = getChildAt(i)
//            if (child.visibility != View.GONE) {
            val lp = child.layoutParams as LinearLayout.LayoutParams
            val weight = lp.weight
            Log.d("FloatFrameLayout", "weight:$weight hSize:$hSize")
            val useExcessSpace = weight > 0f && lp.height == 0
            var spec = heightMeasureSpec
            if (useExcessSpace && hMode == MeasureSpec.EXACTLY) {
                spec = MeasureSpec.makeMeasureSpec(hSize - totalHeight, MeasureSpec.EXACTLY)
                Log.d("FloatFrameLayout", "specH: ${MeasureSpec.getSize(spec)}")
                if (layoutMode != LayoutMode.Linear) {
                    child.measure(widthMeasureSpec, heightMeasureSpec)
                } else {
                    child.measure(widthMeasureSpec, spec)
                }
            } else {
                measureChild(child, widthMeasureSpec, heightMeasureSpec)
            }
            totalHeight += child.measuredHeight
//            }
        }

        if (wMode != MeasureSpec.EXACTLY) {
            wSize = children.map { it.measuredWidth }.reduce { acc, i -> max(acc, i) }
        }

        if (hMode != MeasureSpec.EXACTLY) {
            if (layoutMode == LayoutMode.Float || layoutMode == LayoutMode.FloatReverse) {
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
            children.toMutableList().reversed().forEach {
                it.layout(left, top, left + it.measuredWidth, top + it.measuredHeight)
            }
        }
    }

    private fun layoutLinear(left: Int, top: Int, right: Int, bottom: Int) {
        var childTop = top
        children.forEach {
//            val childBottom = min(childTop + it.measuredHeight, measuredHeight)
            it.layout(left, childTop, left + it.measuredWidth, childTop + it.measuredHeight)
            Log.d("FloatFrameLayout", "childBottom:${childTop + it.measuredHeight}")
            childTop += it.measuredHeight
        }
    }

    override fun addView(child: View?, index: Int, params: ViewGroup.LayoutParams?) {
        super.addView(child, index, params)
        if (childCount > 2) {
            throw IllegalArgumentException("不能超过两个child View!")
        }
    }

    override fun getChildDrawingOrder(childCount: Int, drawingPosition: Int): Int {
        Log.d("getChildDrawingOrder", "childCount : $childCount pos: $drawingPosition")
        if (layoutMode == LayoutMode.FloatReverse) {
            return childCount - drawingPosition - 1
        }
        return super.getChildDrawingOrder(childCount, drawingPosition)

    }

    override fun generateLayoutParams(attrs: AttributeSet?): ViewGroup.LayoutParams {
        return LinearLayout.LayoutParams(context, attrs)
    }

    enum class LayoutMode {
        Linear, Float, FloatReverse
    }

}
