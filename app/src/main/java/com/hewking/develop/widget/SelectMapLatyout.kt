package com.hewking.develop.widget

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.core.content.ContextCompat
import com.hewking.develop.R
import com.hewking.develop.ktx.toDp
import kotlinx.android.synthetic.main.item_left_select.view.*
import kotlin.random.Random

/**
 *@Description: (用一句话描述该文件做什么)
 *@Author: jianhao
 *@Date:   2020/10/29 7:06 PM
 *@License: Copyright Since 2020 Hive Box Technology. All rights reserved.
 *@Notice: This content is limited to the internal circulation of Hive Box,
 and it is prohibited to leak or used for other commercial purposes.
 */
class SelectMapLatyout @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
    , defStyleAttr: Int = 0
) : RadioGroup(context, attrs) {

    var items = arrayOf("全国", "大区", "城市", "网格")

    init {
        orientation = LinearLayout.VERTICAL
        showDividers = SHOW_DIVIDER_MIDDLE
        dividerPadding = 12.toDp().toInt()
        dividerDrawable = ContextCompat.getDrawable(context, R.drawable.shape_space_medium)
        addAllItems()

    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
    }

    private fun addAllItems() {
        items.forEachIndexed { index, s ->
            addItem(s, index)
        }
    }

    fun addItem(item: String, index: Int) {
        val child = LayoutInflater.from(context).inflate(R.layout.item_left_select, this, false)
        child.tv_title_name.text = item
        child.id = Random.nextInt()
        if (index == 0) {
            (child.layoutParams as LinearLayout.LayoutParams).topMargin = 0
        }

        if (index == items.size - 1) {
            (child.layoutParams as LinearLayout.LayoutParams).bottomMargin = 0
        }

        addView(child, index, child.layoutParams)
    }

}