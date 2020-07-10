package com.hewking.develop.basics

import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import org.junit.Before
import org.junit.Test

/**
 * @author: jianhao
 * @create: 2020/7/8
 * @description:
 */
class LiveDataTest {

    private val observable = LiveObservable()

    @Before
    fun beforeTest(){

    }

    @Test
    fun test(){

    }

}

class LiveObservable: LiveData<String>(){

    override fun setValue(value: String?) {
        super.setValue(value)
    }

}

class LiveObserver: Observer<String>{
    override fun onChanged(t: String?) {
        println("liveObserver onchanged: $t")
    }

}