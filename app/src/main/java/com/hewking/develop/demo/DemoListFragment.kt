package com.hewking.develop.demo

import android.content.Intent
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
import com.hewking.develop.ktx.addOrReplaceFragment
import com.hewking.develop.ktx.addOrShowFragment
import com.hewking.develop.ktx.dp2px
import com.hewking.develop.widget.MaskLinearLayout
import kotlinx.android.synthetic.main.activity_main.view.*
import kotlinx.coroutines.internal.AtomicOp
import java.util.concurrent.atomic.AtomicInteger

class DemoListFragment : Fragment() {

    private val datas = mutableListOf<Item>().also {
        var index = 0
        it.add(Item(index++, "Service Demo", ServiceDemoFragment::class.java))
        it.add(Item(index++, "Test Demo", TestDemoFragment::class.java))
        it.add(Item(index++, "TestListFragment", TestListFragment::class.java))
        it.add(Item(index++, "TestDialogFragment", TestDialogFragment::class.java))
        it.add(Item(index++, "ResDemoFragment", ResDemoFragment::class.java))
        it.add(Item(index++, "CoroutineDemoFragment", CoroutineDemoFragment::class.java))
        it.add(Item(index++, "Test Toolbar Demo", ToolbarFragment::class.java))
        it.add(Item(index++, "Android 10 MediaStore Demo", CompatAndroidQActivity::class.java))
        it.add(Item(index++, "Drawable Demo", DrawableDemofragment::class.java))
        it.add(Item(index++, "WebViewFragment Demo", WebViewFragment::class.java))
        it.add(Item(index++, "UAPMFragment Demo", UAPMFragment::class.java))
        it.add(Item(index++, "聊天", MessageFragment::class.java))
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        context ?: return null
        val parent = FrameLayout(requireContext())
        parent.layoutParams = FrameLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
        RecyclerView(requireContext()).also {
            it.layoutManager = LinearLayoutManager(context)
            it.addItemDecoration(
                DividerItemDecoration(
                    requireContext(),
                    DividerItemDecoration.VERTICAL
                )
            )
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

                val itemView = MaskLinearLayout(context!!, null).also { it ->
                    it.layoutParams = (ViewGroup.LayoutParams(-1, -2))
                    // TODO("添加ripple drawable metail 涟漪效果")
//                    it.background = RippleDrawable(ColorStateList.valueOf("#20000000".toColorInt()),
//                    StateListDrawable().apply {
//                        addState(intArrayOf(android.R.attr.state_pressed,android.R.attr.state_selected)
//                        ,ColorDrawable(ContextCompat.getColor(requireContext(),R.color.colorPrimary)))
//                    },null)
                    it.background =
                        requireContext().getDrawable(android.R.drawable.list_selector_background)
                    it.addView(TextView(context).also {
                        it.id = tvID
                        it.setPadding(dp2px(16f).toInt())
                    }, LinearLayout.LayoutParams(-1, -2).also {
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
                        Log.d("click", "execute")
                        val clazz = datas[position].clazz
                        if (Fragment::class.java.isAssignableFrom(clazz)) {
                            val fragment = datas[position].clazz.newInstance()
                            activity?.addOrShowFragment(
                                R.id.frameLayout,
                                fragment as Fragment,
                                datas[position].name
                            )
                        } else {
                            startActivity(Intent(requireContext(), clazz))
                        }

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
        val clazz: Class<*>
    )

}