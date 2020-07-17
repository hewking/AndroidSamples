package com.hewking.develop.demo.dialog

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.*
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.setPadding
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
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
class BottomItemsDialog : DialogFragment() {

    private lateinit var binding: BottomItemsDialogBinding

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog =  super.onCreateDialog(savedInstanceState)
        dialog.setCanceledOnTouchOutside(true)
        dialog.window?.run {
            setGravity(Gravity.BOTTOM)
            setLayout(-1,-2)
            setWindowAnimations(R.style.BottomDialog_Animation)
        }
        return dialog
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(DialogFragment.STYLE_NO_FRAME,R.style.BottomDialog)

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
        binding.root.run {
            val lp = ViewGroup.LayoutParams(-1,-2)
            layoutParams = lp
        }
        binding.recyclerView.run {
            val lp = layoutParams as ViewGroup.LayoutParams
            lp.width = resources.displayMetrics.widthPixels
            lp.height = (resources.displayMetrics.heightPixels * 0.8).toInt()
            layoutParams
        }
        initView()

    }

    private fun initView() {
        binding.recyclerView.run {
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