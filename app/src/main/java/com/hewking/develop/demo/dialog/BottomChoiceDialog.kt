package com.hewking.develop.demo.dialog

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.Gravity
import com.hewking.develop.R
import com.hewking.develop.databinding.DialogContentNormalBinding

/**
 * @author: jianhao
 * @create: 2020/7/17
 * @description:
 */
class BottomChoiceDialog(ctx: Context) : Dialog(ctx, R.style.BottomDialog){

    private lateinit var binding: DialogContentNormalBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DialogContentNormalBinding.inflate(layoutInflater,null,false)
        setContentView(binding.root)

        val params = binding.root.layoutParams
        params.width = context.resources.displayMetrics.widthPixels
        params.height = (context.resources.displayMetrics.heightPixels * 0.8).toInt()
        binding.root.layoutParams = params
        window?.setGravity(Gravity.BOTTOM)
        window?.setWindowAnimations(R.style.BottomDialog_Animation)
        setCanceledOnTouchOutside(true)
    }


}