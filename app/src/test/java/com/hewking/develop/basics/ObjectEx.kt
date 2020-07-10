package com.hewking.develop.basics

/**
 * @author: jianhao
 * @create: 2020/7/9
 * @description:
 */

inline fun <T,R> T.map(transform: T.() -> R): R{
    return transform()
}