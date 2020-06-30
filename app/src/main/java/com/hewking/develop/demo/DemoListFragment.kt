package com.hewking.develop.demo

import android.content.res.ColorStateList
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.RippleDrawable
import android.graphics.drawable.StateListDrawable
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import androidx.core.graphics.toColorInt
import androidx.core.view.setPadding
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hewking.develop.R
import com.hewking.develop.ktx.dp2px
import kotlinx.android.synthetic.main.activity_main.view.*

class DemoListFragment : Fragment() {

    private val datas = mutableListOf<Item>().also {
        var index = 1
        it.add(Item(index++, "Service Demo", ServiceDemoFragment::class.java))
        it.add(Item(index++, "Test Demo", TestDemoFragment::class.java))
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        context ?: return null
        val parent = FrameLayout(context!!)
        parent.layoutParams = FrameLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
        RecyclerView(context!!).also {
            it.layoutManager = LinearLayoutManager(context)
            it.addItemDecoration(DividerItemDecoration(requireContext(),DividerItemDecoration.VERTICAL))
            it.adapter = buildAdpater()
            parent.addView(it, FrameLayout.LayoutParams(-1, -1))
        }
        return parent
    }

    private fun buildAdpater(): RecyclerView.Adapter<RecyclerView.ViewHolder>? {
        return object : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

            private val tvID = View.generateViewId()

            override fun onCreateViewHolder(
                parent: ViewGroup,
                viewType: Int
            ): RecyclerView.ViewHolder {

                val itemView = LinearLayout(context).also { it ->
                    it.layoutParams = (ViewGroup.LayoutParams(-1,-2))
                    // TODO("添加ripple drawable metail 涟漪效果")
                    it.background = RippleDrawable(ColorStateList.valueOf("#20000000".toColorInt()),
                    StateListDrawable().apply {
                        addState(intArrayOf(android.R.attr.state_pressed,android.R.attr.state_selected)
                        ,ColorDrawable(ContextCompat.getColor(requireContext(),R.color.colorPrimary)))
                    },null)
                    it.addView(TextView(context).also {
                        it.id = tvID
                        it.setPadding(dp2px(16f).toInt())
                    },LinearLayout.LayoutParams(-1,-2).also {
                        it.gravity = Gravity.CENTER
                    })
                }
                return object : RecyclerView.ViewHolder(itemView) {

                }
            }

            override fun getItemCount(): Int {
                return datas.size
            }

            override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
                val tvName = holder.itemView.findViewById<TextView>(tvID)
                tvName.also {
                    it.text = datas[position].name
                    holder.itemView.setOnClickListener {
                        Log.d("click","execute")
                        val transaction = activity!!.supportFragmentManager.beginTransaction()
                        val fragment = datas[position].clazz.newInstance()
                        transaction.add(R.id.frameLayout,fragment,datas[position].name)
                        transaction.addToBackStack(datas[position].name)
                        transaction.commit()
                    }
                }
            }

        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    data class Item(
        val id: Int,
        val name: String,
        val clazz: Class<out Fragment>
    )

}