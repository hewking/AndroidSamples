package com.hewking.develop.demo.dialog

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.*
import androidx.core.view.setPadding
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.hewking.develop.R
import com.hewking.develop.databinding.BottomItemsDialogBinding
import com.hewking.develop.databinding.DemoListItemBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


/**
 * @author: jianhao
 * @create: 2020/7/16
 * @description:
 */
class BottomItemsSheetDialog() : BottomSheetDialogFragment() {

    private lateinit var binding: BottomItemsDialogBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = BottomItemsDialogBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
//        dialog.window?.run {
////            setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
//            val lp = attributes
//            lp.height = (resources.displayMetrics.heightPixels * 0.9).toInt()
//            attributes = lp
//        }
        return dialog
    }

    private fun initView() {
        binding.root.apply {

        }
        binding.recyclerView.run {
            val lp = layoutParams
            lp.height = (resources.displayMetrics.heightPixels * 0.9f).toInt()
            layoutManager = GridLayoutManager(requireContext(), 3)
            lifecycleScope.launch {
                adapter = ItemsAdapter(datas = buildData())
            }
        }
    }

    class ItemViewHolder(private val binding: DemoListItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(data:String) {
            binding.tvTitle.text = " 这是标题"
            binding.tvSubtitle.text = "这是子标题$data"
            binding.tvTime.text = "时间"
        }
    }

    suspend fun buildData(): MutableList<String>{
        val datas = withContext(Dispatchers.IO) {
            mutableListOf<String>().apply {
                for (i in 0 until 10) {
                    add("我是数据 $i")
                }
            }
        }
        return datas
    }

    class ItemsAdapter(private val datas: List<String>) : RecyclerView.Adapter<ItemViewHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
            return ItemViewHolder(
                DemoListItemBinding.inflate(
                LayoutInflater.from(
                    parent.context
                ),
                    parent,
                    false
                )
            )
        }

        override fun getItemCount(): Int = datas.size

        override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
            holder.bind(datas[position])
        }

    }


}