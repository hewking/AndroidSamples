package com.hewking.develop.demo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.graphics.ColorUtils
import androidx.fragment.app.Fragment
import com.hewking.develop.databinding.TestFragmentBinding
import com.hewking.develop.demo.dialog.BottomDialogDemo
import com.hewking.develop.demo.dialog.BottomItemsDialog
import com.hewking.develop.demo.dialog.CustomDialog
import com.hewking.webview.LogUtil
import com.tencent.bugly.crashreport.CrashReport
import kotlinx.android.synthetic.main.test_fragment.*

class TestDemoFragment : Fragment() {

    private lateinit var binding: TestFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = TestFragmentBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnChange.setOnClickListener {

            val color = ColorUtils.setAlphaComponent(iv_text.currentTextColor,0x80)
            iv_text.setTextColor(color)

            LogUtil.e("怎么回事啊","又不回消息了")

        }

        binding.btnChange2.setOnClickListener {
            ll_mask.showMask = true

            CrashReport.testJavaCrash()
        }

        binding.listItem.run {
            tvTitle.text = "小美女"
            tvSubtitle.text = "家里了"
        }

        binding.btnShowDialog.setOnClickListener {
            CustomDialog.Builder(requireContext())
                .setTitle("标题咋样")
                .setMessage("消息里面的呢从有哪些呢搞起来")
                .setPositiveButton("确定") { dialog, which ->
                    dialog.dismiss()

                }.setNegativeButton("取消",{
                    dialog, which ->
                    dialog.dismiss()
                })

                .create().apply {
                    show()
                    val params = window?.attributes
                    params?.apply {
//                        width = 270.toDpi()
                    }

                    println("dialog height:${params?.height} width:${params?.width}")
//                    window?.setLayout(270.toDpi(),-2)
                }
        }

//        iv_gif.load(R.drawable.ic_select_box)
        binding.btnShowDialog2.setOnClickListener {
            val dialog = BottomItemsDialog()
            dialog.show(fragmentManager?:return@setOnClickListener,BottomItemsDialog::class.java.simpleName)

//            val dialog = BottomItemsSheetDialog()
//            dialog.show(childFragmentManager,"dialog")

//            val dialog = BottomChoiceDialog(requireContext())
//            dialog.show()
        }

        binding.btnShowDialog4.setOnClickListener {
            val dialog = BottomDialogDemo(context)
            dialog.show()
        }

    }

}