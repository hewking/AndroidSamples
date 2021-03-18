package com.hewking.develop.demo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hewking.develop.databinding.FragmentMessageBinding
import com.hewking.develop.databinding.ItemMessageBinding
import com.hewking.develop.db.dao.MessageDao
//import com.hewking.develop.db.dao.MessageDao
import com.hewking.develop.db.table.MessageTable
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * @Description: (用一句话描述该文件做什么)
 * @Author: jianhao
 * @Date:   2021/3/18 11:25
 * @License: Copyright Since 2020 Hive Box Technology. All rights reserved.
 * @Notice: This content is limited to the internal circulation of Hive Box, 
and it is prohibited to leak or used for other commercial purposes.
 */
class MessageFragment: Fragment() {

    private lateinit var binding: FragmentMessageBinding

    private lateinit var adapter: MessageAdapter

    private val messageDao = MessageDao()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMessageBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = MessageAdapter()
        binding.rvList.adapter = adapter
        binding.rvList.apply {
            itemAnimator = DefaultItemAnimator()
            layoutManager = LinearLayoutManager(requireContext())
        }

        binding.btnSend.setOnClickListener {

            val text = binding.etMessage.text.toString()
            if (text.isBlank()) {
                Toast.makeText(requireContext(), "消息无效!", Toast.LENGTH_SHORT)
                return@setOnClickListener
            }

            addMessage(text)

        }

        fetchData()

    }

    private fun fetchData() {
        lifecycleScope.launch {
            withContext(Dispatchers.IO) {
                messageDao.queryAllBlock().map {
                    Message(it.message)
                }
            }.also {
                adapter.setDatas(it)
            }
        }
    }

    private fun addMessage(message_: String) {
        val message = Message(message_)
        adapter.addMessage(message)
        lifecycleScope.launch {
            withContext(Dispatchers.IO) {
                messageDao.saveBlock(MessageTable().also {
                    it.message = message_
                })
            }
        }
    }

    class MessageAdapter: RecyclerView.Adapter<ViewHolder>(){

        private val mDatas = mutableListOf<Message>()

        fun setDatas(datas: List<Message>) {
            mDatas.clear()
            mDatas.addAll(datas)
            notifyDataSetChanged()
        }

        fun addMessage(message: Message) {
            mDatas.add(message)
            notifyItemInserted(mDatas.lastIndex)
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            return ViewHolder(ItemMessageBinding.inflate(LayoutInflater.from(parent.context)))
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.binding.tvMessage.text = mDatas[position].message
        }

        override fun getItemCount(): Int {
            return mDatas.size
        }

    }

    class ViewHolder(val binding: ItemMessageBinding): RecyclerView.ViewHolder(binding.root)

    class Message(val message: String)

}