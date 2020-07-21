package com.hewking.develop.demo

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatDialogFragment
import androidx.core.view.setPadding
import com.hewking.develop.demo.dialog.BottomChoiceDialog
import com.hewking.develop.demo.dialog.BottomItemsDialog
import com.hewking.develop.demo.dialog.BottomItemsSheetDialog
import com.hewking.develop.demo.dialog.CustomDialog
import com.hewking.develop.ktx.dp2px

/**
 * @author: jianhao
 * @create: 2020/7/17
 * @description: 一些dialog demo
 * https://juejin.im/post/59c10f115188257e876a0d47 一些可以参考的文章
 */
class TestDialogFragment : AppCompatDialogFragment(){

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val container = LinearLayout(context).apply {
            layoutParams = ViewGroup.LayoutParams(-1,-1)
            orientation = LinearLayout.VERTICAL
            setBackgroundColor(Color.WHITE)
            showDividers = LinearLayout.SHOW_DIVIDER_MIDDLE
            dividerDrawable = ColorDrawable(Color.LTGRAY)
            getItems().forEach {content ->
                val item = TextView(context).apply{
                    text = content
                    setPadding(dp2px(16f).toInt(), dp2px(10f).toInt(), dp2px(16f).toInt(), dp2px(10f).toInt())
                    setOnClickListener {
                        handlerShowDialog(content)
                    }
                }
                addView(item,LinearLayout.LayoutParams(-1,-2).apply {
                    gravity = Gravity.CENTER_VERTICAL
                })
            }
        }

        return container
    }

    private fun handlerShowDialog(content: String) {
        when(content) {
            "BottomChoiceDialog" -> {
                BottomChoiceDialog(requireContext()).show()
            }
            "BottomItemsDialog" -> {
                BottomItemsDialog().show(childFragmentManager,"dialog")
            }
            "BottomItemsSheetDialog" -> {
                BottomItemsSheetDialog().show(childFragmentManager,"dialog")
            }
            "CustomDialog" -> {
                val dialog = CustomDialog.Builder(requireContext()).setTitle("这是标题")
                    .setMessage("一夜回到解放前")
                    .setNegativeButton("取消", {dialog,which ->
                        dialog?.dismiss()
                    })
                    .setPositiveButton("确定",{dialog,which ->
                        dialog?.dismiss()
                    }).create()
                dialog.show()
            }
        }
    }

    private fun getItems(): List<String> {
        return listOf("BottomChoiceDialog",
        "BottomItemsDialog",
            "BottomItemsSheetDialog",
        "CustomDialog")
    }

}