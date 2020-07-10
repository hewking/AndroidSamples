package com.hewking.develop.basics

/**
 * @author: jianhao
 * @create: 2020/7/8
 * @description:
 */
class TestStaticFunc {

    companion object{

        /**
         * 会使用静态方法
         */
        @JvmStatic
        fun test(){

        }

        /**
         * 被静态初始化的Companion对象的 方法
         */
        fun bar(){

        }
    }



}