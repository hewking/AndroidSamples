package com.hewking.develop.widget

import android.content.Context
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.widget.LinearLayout
import com.hewking.develop.R

/**
 * 项目名称：FlowChat
 * 类的描述：
 * 创建人员：hewking
 * 创建时间：2018/8/24 16:03
 * 修改人员：hewking
 * 修改时间：2018/8/24 16:03
 * 修改备注：
 * Version: 1.0.0
 */
class MaskLinearLayout(ctx: Context, attrs: AttributeSet?) : LinearLayout(ctx, attrs) {

    constructor(ctx:Context):this(ctx,null)

    var showMask = false
        set(value) {
            field = value
           /* if (!field) {
                mask = null
            } else {
                update()
            }*/
            update()
        }
    var mask: Drawable? = null
        set(value) {
            field = value
//            showMask = true
        }

    var rightOffset: Int = 0
        set(value) {
            field = value
            update()
        }

    var leftOffset: Int = 0
        set(value) {
            field = value
            update()
        }

    init {
        val typeArray = ctx.obtainStyledAttributes(attrs, R.styleable.MaskLinearLayout)

        showMask = typeArray.getBoolean(R.styleable.MaskLinearLayout_mask_showmask, false)
        mask = typeArray.getDrawable(R.styleable.MaskLinearLayout_mask_drawable)
        rightOffset = typeArray.getDimensionPixelSize(R.styleable.MaskLinearLayout_mask_right_offset, rightOffset)
        leftOffset = typeArray.getDimensionPixelSize(R.styleable.MaskLinearLayout_mask_left_offset, leftOffset)

        typeArray.recycle()
    }

    private fun update() {
        requestLayout()
        invalidate()
    }

    override fun dispatchDraw(canvas: Canvas?) {
        super.dispatchDraw(canvas)
        canvas?:return
        if (showMask) {
            mask?.setBounds(leftOffset, 0, width - rightOffset, height)
            mask?.draw(canvas)
        }
    }
}