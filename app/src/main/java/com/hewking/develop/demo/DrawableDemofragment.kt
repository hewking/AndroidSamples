package com.hewking.develop.demo

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.graphics.ColorUtils
import androidx.fragment.app.Fragment
import com.hewking.develop.databinding.FragmentDrawableDemoBinding
import com.hewking.develop.databinding.TestFragmentBinding
import com.hewking.develop.demo.dialog.BottomDialogDemo
import com.hewking.develop.demo.dialog.BottomItemsDialog
import com.hewking.develop.demo.dialog.CustomDialog
import kotlinx.android.synthetic.main.fragment_drawable_demo.*
import kotlinx.android.synthetic.main.test_fragment.*

class DrawableDemofragment : Fragment() {

    private lateinit var binding: FragmentDrawableDemoBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDrawableDemoBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        shadowLayout.setmShadowColor(Color.parseColor("#ffdcca90"))

    }

}