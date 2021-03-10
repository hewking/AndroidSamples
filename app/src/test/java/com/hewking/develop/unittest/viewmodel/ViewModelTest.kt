package com.hewking.develop.unittest.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.hewking.develop.unittest.MyApp
import com.hewking.develop.unittest.UnitTestActivity
import io.mockk.InternalPlatformDsl
import io.mockk.MockKAnnotations
import io.mockk.spyk
import kt.com.fcbox.hiveconsumer.app.utils.CoroutinesMainDispatcherRule
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment
import org.robolectric.android.controller.ActivityController
import org.robolectric.annotation.Config
import org.robolectric.shadows.ShadowLog

/**
 * @Description: (用一句话描述该文件做什么)
 * @Author: jianhao
 * @Date:   2021/3/10 13:47
 * @License: Copyright Since 2020 Hive Box Technology. All rights reserved.
 * @Notice: This content is limited to the internal circulation of Hive Box, 
and it is prohibited to leak or used for other commercial purposes.
 */
@RunWith(RobolectricTestRunner::class)
@Config(sdk = [26], application = MyApp::class)
class ViewModelTest {

    @get:Rule
    var coroutinesMainDispatcherRule = CoroutinesMainDispatcherRule()

    lateinit var activityController: ActivityController<UnitTestActivity>
    lateinit var unitTestActivity: UnitTestActivity

    @Before
    fun setup() {
        //输出日志
        ShadowLog.stream = System.out
        MockKAnnotations.init(this, relaxed = true)

        activityController = Robolectric
            .buildActivity(UnitTestActivity::class.java)
        unitTestActivity = activityController.create().get()
    }

    @Test
    fun `test viewmodel setValue`(){
        val vm = spyk<TestViewModel>(recordPrivateCalls = true)
        val liveData = spyk<MutableLiveData<Int>>()

        InternalPlatformDsl.dynamicSetField(vm, "liveData", liveData)

        vm.liveData.observe(unitTestActivity, {
            println("value:$it")
        })

        vm.liveData.value = 1
        vm.liveData.value = 2
        vm.liveData.value = 3
        vm.liveData.value = 4
        vm.liveData.value = 5

    }

    class TestViewModel : ViewModel(){

        val liveData = MutableLiveData<Int>()

    }

}