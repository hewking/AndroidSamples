package com.hewking.develop.demo.wanandroid

import com.hewking.develop.demo.wanandroid.api.WanAndroidApi
import com.hewking.develop.demo.wanandroid.repository.DefaultWanAndroidRepository
import com.hewking.develop.demo.wanandroid.repository.WanAndroidRepository

interface ServiceLocator {

    companion object {

        private val LOCK = Any()
        private var instance: ServiceLocator? = null

        fun instance(): ServiceLocator {
            synchronized(LOCK) {
                if (instance == null) {
                    instance = DefaultServiceLocator()
                }
                return instance!!
            }
        }

    }

    fun getApi(): WanAndroidApi

    fun getRepository(): WanAndroidRepository

}

open class DefaultServiceLocator : ServiceLocator {

    override fun getApi(): WanAndroidApi {
        return WanAndroidApi.create()
    }

    override fun getRepository(): WanAndroidRepository {
        return DefaultWanAndroidRepository(getApi())
    }

}