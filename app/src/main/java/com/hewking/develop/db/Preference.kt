package com.hewking.develop.db

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import com.hewking.develop.app.DemoApplication
import kotlin.reflect.KProperty

/**
 * @author: salmonzhg
 * @create: 2019/12/19
 * @description: Delegate styled preference.
 */
class Preference<T>(val name: String, private val default: T) {
  private val prefs: SharedPreferences by lazy {
   DemoApplication.get().getSharedPreferences("app", Context.MODE_PRIVATE)
  }

  operator fun getValue(thisRef: Any?, property: KProperty<*>): T {
    return getSharePreferences(name, default)
  }

  operator fun setValue(thisRef: Any?, property: KProperty<*>, value: T) {
    putSharePreferences(name, value)
  }

  @SuppressLint("CommitPrefEdits")
  private fun putSharePreferences(name: String, value: T) = with(prefs.edit()) {
    when (value) {
      is Long -> putLong(name, value)
      is String -> putString(name, value)
      is Int -> putInt(name, value)
      is Boolean -> putBoolean(name, value)
      is Float -> putFloat(name, value)
      else -> this
    }.apply()
  }

  @Suppress("UNCHECKED_CAST")
  private fun getSharePreferences(name: String, default: T): T = with(prefs) {
    val res: Any? = when (default) {
      is Long -> getLong(name, default)
      is String -> getString(name, default)
      is Int -> getInt(name, default)
      is Boolean -> getBoolean(name, default)
      is Float -> getFloat(name, default)
      else -> default as Any
    }
    return res as T
  }
}