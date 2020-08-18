package com.hewking.develop.demo

import android.os.Bundle
import android.text.format.DateUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.*
import com.hewking.develop.R
import com.hewking.develop.databinding.TestListFragmentBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

class TestListFragment : Fragment() {

    companion object{
        const val TAG: String = "TestListFragment"
    }

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
        initToolbar()
        initView()

    }

    private fun initToolbar() {
        binding.toolbar.apply {
            setNavigationOnClickListener {
                fragmentManager?.popBackStack()
            }
        }
    }

    private fun initView() {
        binding.rvList.apply {
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)
            addItemDecoration(DividerItemDecoration(requireContext(), RecyclerView.VERTICAL))
            itemAnimator = DefaultItemAnimator()
            lifecycleScope.launch {
                val datas = withContext(Dispatchers.IO) {
                    buildData()
                }
                adapter = ConcatAdapter(ListAdapter(datas),LoadingAdapter())
            }
        }
    }

    suspend fun buildData(): List<Data> {
        val datas = mutableListOf<Data>()
        for (i in 0 until 20) {
            datas.add(Data(title = "太难了 ${i + 1}",
            subtitle = "咋整额，搞不定了好累",
            time = SimpleDateFormat("yyyy-MM-dd hh").format(System.currentTimeMillis())))
        }
        return datas
    }

    class ListAdapter(private val datas: List<Data>) : RecyclerView.Adapter<VH>() {
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

    class LoadingAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>(){
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
            return LoadingViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_list_loading,
            parent,false))
        }

        override fun getItemCount(): Int {
            return 1
        }

        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        }

    }

    class LoadingViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)

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