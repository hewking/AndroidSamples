package com.hewking.develop.demo

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.hewking.develop.R
import kotlinx.android.synthetic.main.coroutine_demo_fragment.*
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.ticker
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.util.*

/**
 * @author: jianhao
 * @create: 2020/7/21
 * @description:
 */
class CoroutineDemoFragment : Fragment(){

    private val tickerChannel = ticker(1000L,0)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.coroutine_demo_fragment,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btn_ticker.setOnClickListener {
            GlobalScope.launch {
                withContext(Dispatchers.Main) {
                    for (event in tickerChannel) {
                        launch(Dispatchers.Main) {
                            val time = SimpleDateFormat("hh::mm::ss").format(Date().time)
                            Log.d("btn_ticker","$time")
                            btn_ticker.text = time
                        }
                    }
                }
            }
        }

        btn_cancel_ticker.setOnClickListener {
            tickerChannel.cancel()
        }

        // rxjava
        btn_rx.setOnClickListener {

        }

        btn_rx_stop.setOnClickListener {

        }

    }

    override fun onDestroy() {
        super.onDestroy()
        tickerChannel.cancel()
    }



}