package com.hewking.develop.vm

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * @Description: (用一句话描述该文件做什么)
 * @Author: jianhao
 * @Date:   2021/3/17 18:14
 * @License: Copyright Since 2020 Hive Box Technology. All rights reserved.
 * @Notice: This content is limited to the internal circulation of Hive Box, 
and it is prohibited to leak or used for other commercial purposes.
 */
class MainViewModel: ViewModel() {

    val triggerLiveData = MutableLiveData<Unit>()

    fun doLongTask(){
        // 耗时5s
        GlobalScope.launch {
            for (i in 5 downTo 0) {
                Log.d("doLongTask"," value :$i")
                delay(1000L)
            }
            triggerLiveData.postValue(Unit)
        }
    }

}