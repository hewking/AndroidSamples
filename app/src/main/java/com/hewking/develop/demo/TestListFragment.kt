package com.hewking.develop.demo

import android.os.Bundle
import android.text.format.DateUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.hewking.develop.R
import com.hewking.develop.databinding.TestListFragmentBinding
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

class TestListFragment : Fragment() {

    private lateinit var binding: TestListFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = TestListFragmentBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()

    }

    private fun initView() {
        val datas = buildData()
        Log.d("TestListFragment",datas.map { it.subtitle }.reduce { acc, data ->
            acc + data
        })
        binding.rvList.apply {
            addItemDecoration(DividerItemDecoration(requireContext(), RecyclerView.VERTICAL))
            itemAnimator = DefaultItemAnimator()
        }
        binding.rvList.adapter = ListAdapter(datas)

    }

    fun buildData(): List<Data> {
        val datas = mutableListOf<Data>()
        for (i in 0 until 20) {
            datas.add(Data(title = "太难了 ${i + 1}",
            subtitle = "咋整额，搞不定了好累",
            time = SimpleDateFormat("yyyy-MM-DD hh").format(System.currentTimeMillis())))
        }
        return datas
    }

    class ListAdapter(val datas: List<Data>) : RecyclerView.Adapter<VH>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
            return VH(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.demo_list_item, parent, false)
            )
        }

        override fun getItemCount(): Int {
            return datas.size
        }

        override fun onBindViewHolder(holder: VH, position: Int) {
            holder.bind(datas[position])
        }

    }

    class VH(view: View) : RecyclerView.ViewHolder(view) {

        fun bind(data:Data) {
            data.also {
                itemView.findViewById<TextView>(R.id.tv_title).text = data.title
                itemView.findViewById<TextView>(R.id.tv_subtitle).text = data.subtitle
                itemView.findViewById<TextView>(R.id.tv_time).text = data.time
            }
        }

    }

    data class Data(
        val title: String,
        val subtitle: String,
        val time: String
    )

}