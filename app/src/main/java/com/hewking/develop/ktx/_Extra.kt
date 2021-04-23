package com.hewking.develop.ktx

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import java.io.Serializable
import kotlin.reflect.KProperty

/**
 * @Description: Activity Extra, Intent， Bundle 获取值的拓展
 *
 * ``` kotlin
 *
 * 1. Activity.extra
 *
 * eg:
 *
 * private val name  = extra("name","tom")
 *
 * 2. Intent.getExtra
 *
 * eg:
 *
 * val name = intent.getExtra<String>("name", "tom")
 *
 * 3. Bundle.getExtra
 *
 * eg:
 *
 * val bundle = intent.getExtra()
 * val name = bundle.getExtra<String>("name", "tom")
 *
 * ```
 *
 * 3. createBundle
 *
 * eg:
 *
 * val bundle = createBundle(arrayOf("name" to "tom", "age" to 18))
 *
 * @Author: jianhao
 * @Date:   2021/4/16 11:10
 * @License: Copyright Since 2020 Hive Box Technology. All rights reserved.
 * @Notice: This content is limited to the internal circulation of Hive Box, 
 * and it is prohibited to leak or used for other commercial purposes.
 */
class Extra<T>(private val activity: Activity, val name: String, private val default: T) {

  operator fun getValue(thisRef: Any?, property: KProperty<*>): T? {
    return getExtra(name, default)
  }

  @Suppress("UNCHECKED_CAST")
  private fun getExtra(name: String, default: T?): T? {
    val res: Any? = when (default) {
      is Int -> activity.intent?.getIntExtra(name, default)
      is Long -> activity.intent?.getLongExtra(name, default)
      is String -> activity.intent?.getStringExtra(name)
      is Boolean -> activity.intent?.getBooleanExtra(name, default)
      is Float -> activity.intent?.getFloatExtra(name, default)
      is Short -> activity.intent?.getShortExtra(name, default)
      is Byte -> activity.intent?.getByteExtra(name, default)
      is Parcelable -> activity.intent?.getParcelableExtra(name) ?: default
      is Serializable -> activity.intent?.getSerializableExtra(name) ?: default
      else -> default
    } ?: default
    return res as T?
  }
}


/**
 * 内部类,解析传递参数值
 */
object ArgInternals {

  fun createBundle(params: Array<out Pair<String, Any?>>): Bundle = Bundle().apply {
    if (params.isNotEmpty()) putBundleArguments(this, params)
  }

  private fun putBundleArguments(bundle: Bundle, params: Array<out Pair<String, Any?>>) {
    params.forEach {
      bundle.put(it.first,it.second)
    }
  }

  @Suppress("UNCHECKED_CAST")
  fun <T : Any> getIntentArguments(intent: Intent, key: String, default: T?): T? {
    if (!intent.hasExtra(key)) return null
    return when (default) {
      null -> intent.getStringExtra(key) as? T
      is Int -> intent.getIntExtra(key, (default as? Int)
          ?: 0) as? T
      is Long -> intent.getLongExtra(key, (default as? Long)
          ?: 0) as? T
      is CharSequence -> intent.getCharSequenceExtra(key) as? T
      is String -> intent.getStringExtra(key) as? T
      is Float -> intent.getFloatExtra(key, (default as? Float)
          ?: 0.0f) as? T
      is Double -> intent.getDoubleExtra(key, (default as? Double)
          ?: 0.0) as? T
      is Char -> intent.getCharExtra(key, (default as? Char)
          ?: '\u0000') as? T
      is Short -> intent.getShortExtra(key, (default as? Short)
          ?: 0) as? T
      is Boolean -> intent.getBooleanExtra(key, (default as? Boolean)
          ?: false) as? T
      is Serializable -> intent.getSerializableExtra(key) as? T
      is Bundle -> intent.getBundleExtra(key) as? T
      is Parcelable -> (intent.getParcelableExtra(key) as? Parcelable) as? T
      is Array<*> -> when {
        default.isArrayOf<CharSequence>() -> intent.getSerializableExtra(key) as? T
        default.isArrayOf<String>() -> intent.getSerializableExtra(key) as? T
        default.isArrayOf<Parcelable>() -> intent.getSerializableExtra(key) as? T
        else -> illegalArg("Intent extra $key has wrong type ${default.javaClass.name}")
      }
      is IntArray -> intent.getIntArrayExtra(key) as? T
      is LongArray -> intent.getLongArrayExtra(key) as? T
      is FloatArray -> intent.getFloatArrayExtra(key) as? T
      is DoubleArray -> intent.getDoubleArrayExtra(key) as? T
      is CharArray -> intent.getCharArrayExtra(key) as? T
      is ShortArray -> intent.getShortArrayExtra(key) as? T
      is BooleanArray -> intent.getBooleanArrayExtra(key) as? T
      else -> illegalArg("intent extra $key has wrong type ${default.javaClass.name}")
    }
  }


  @Suppress("UNCHECKED_CAST")
  fun <T : Any> getBundleArguments(bundle: Bundle, key: String, default: Any?): T? {
    if (!bundle.containsKey(key)) return null
    return when (default) {
      null -> bundle.getString(key) as? T
      is Int -> bundle.getInt(key, (default as? Int)
          ?: 0) as? T
      is Long -> bundle.getLong(key, (default as? Long) ?: 0) as? T
      is CharSequence -> bundle.getCharSequence(key) as? T
      is String -> bundle.getString(key) as? T
      is Float -> bundle.getFloat(key, (default as? Float) ?: 0.0f) as? T
      is Double -> bundle.getDouble(key, (default as? Double) ?: 0.0) as? T
      is Char -> bundle.getChar(key, (default as? Char) ?: '\u0000') as? T
      is Short -> bundle.getShort(key, (default as? Short) ?: 0) as? T
      is Boolean -> bundle.getBoolean(key, (default as? Boolean)
          ?: false) as? T
      is Serializable -> bundle.getSerializable(key) as? T
      is Bundle -> bundle.getBundle(key) as? T
      is Parcelable -> (bundle.getParcelable(key) as? Parcelable) as? T
      is Array<*> -> when {
        default.isArrayOf<CharSequence>() -> bundle.getCharSequenceArray(key) as? T
        default.isArrayOf<String>() -> bundle.getStringArray(key) as? T
        default.isArrayOf<Parcelable>() -> bundle.getParcelableArray(key) as? T
        else -> illegalArg("Intent extra $key has wrong type ${default.javaClass.name}")
      }
      is IntArray -> bundle.getIntArray(key) as? T
      is LongArray -> bundle.getLongArray(key) as? T
      is FloatArray -> bundle.getFloatArray(key) as? T
      is DoubleArray -> bundle.getDoubleArray(key) as? T
      is CharArray -> bundle.getCharArray(key) as? T
      is ShortArray -> bundle.getShortArray(key) as? T
      is BooleanArray -> bundle.getBooleanArray(key) as? T
      else -> illegalArg("bundle extra $key has wrong type ${default.javaClass.name}")
    }
  }

}

fun createBundle(params: Array<out Pair<String, Any?>>): Bundle = ArgInternals.createBundle(params)
fun <T> Activity.extra(name: String, default: T) = Extra(this, name, default)
fun <T : Any> Intent.getExtra(key: String, default: T?): T? = ArgInternals.getIntentArguments(this, key, default)
fun <T : Any> Bundle.getExtra(key: String, default: T?): T? = ArgInternals.getBundleArguments(this, key, default)
