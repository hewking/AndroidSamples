package com.hewking.develop.ktx

import android.text.Editable
import android.text.TextWatcher
import android.widget.TextView

/**
 * @Description: TextView 的一些拓展方法
 * @Author: jianhao
 * @Date:   2021/4/15 15:09
 * @License: Copyright Since 2020 Hive Box Technology. All rights reserved.
 * @Notice: This content is limited to the internal circulation of Hive Box, 
 * and it is prohibited to leak or used for other commercial purposes.
 */

fun TextView.addOnTextChangedListener(
    config: TextWatcherConfiguration.() -> Unit
) {
    val listener = TextWatcherConfiguration().apply { config() }
    addTextChangedListener(listener)
}

class TextWatcherConfiguration : TextWatcher {

    private var beforeTextChangedCallback: (BeforeTextChangedFunction)? = null
    private var onTextChangedCallback: (OnTextChangedFunction)? = null
    private var afterTextChangedCallback: (AfterTextChangedFunction)? = null

    fun beforeTextChanged(callback: BeforeTextChangedFunction) {
        beforeTextChangedCallback = callback
    }

    fun onTextChanged(callback: OnTextChangedFunction) {
        onTextChangedCallback = callback
    }

    fun afterTextChanged(callback: AfterTextChangedFunction) {
        afterTextChangedCallback = callback
    }

    override fun beforeTextChanged(
        s: CharSequence,
        start: Int,
        count: Int,
        after: Int
    ) {
        beforeTextChangedCallback?.invoke(s.toString(), start, count, after)
    }

    override fun onTextChanged(
        s: CharSequence,
        start: Int,
        before: Int,
        count: Int
    ) {
        onTextChangedCallback?.invoke(s.toString(), start, before, count)
    }

    override fun afterTextChanged(s: Editable) {
        afterTextChangedCallback?.invoke(s)
    }

}

private typealias BeforeTextChangedFunction =
            (text: String, start: Int, count: Int, after: Int) -> Unit

private typealias OnTextChangedFunction =
            (text: String, start: Int, before: Int, count: Int) -> Unit

private typealias AfterTextChangedFunction =
            (s: Editable) -> Unit
