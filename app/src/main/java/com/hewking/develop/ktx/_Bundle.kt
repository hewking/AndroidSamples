package com.hewking.develop.ktx

import android.os.Binder
import android.os.Build.VERSION.SDK_INT
import android.os.Bundle
import android.os.IBinder
import android.os.Parcelable
import android.util.SparseArray

/**
 * @Description: bundle 的拓展
 * @Author: jianhao
 * @Date:   2021/3/24 11:10
 * @License: Copyright Since 2020 Hive Box Technology. All rights reserved.
 * @Notice: This content is limited to the internal circulation of Hive Box, 
 * and it is prohibited to leak or used for other commercial purposes.
 */
fun Bundle.put(key: String, value: Any?) {
  when (value) {
      is String -> putString(key, value)
      is IntArray -> putIntArray(key, value)
      is ShortArray -> putShortArray(key, value)
      is LongArray -> putLongArray(key, value)
      is ByteArray -> putByteArray(key, value)
      is FloatArray -> putFloatArray(key, value)
      is DoubleArray -> putDoubleArray(key, value)
      is BooleanArray -> putBooleanArray(key, value)
      is CharArray -> putCharArray(key, value)
      is CharSequence -> putCharSequence(key, value)
      is Bundle -> putBundle(key, value)
      is Array<*> -> putArray(key, value)
      is ArrayList<*> -> putArrayList(key, value)
      is SparseArray<*> -> putSparseArrayOfParcelable(key, value)
      is Binder -> if (SDK_INT >= 18) putBinder(key, value) else putBinderCompat(key, value)
      is Parcelable -> putParcelable(key, value)
      is java.io.Serializable -> putSerializable(key, value) // Includes primitive types
      null -> putString(key, value) // Any non primitive type works for any null value
    else -> unsupported("Type ${value.javaClass.canonicalName} is not supported")
  }
}

private fun Bundle.putArray(key: String, value: Array<*>) {
  @Suppress("UNCHECKED_CAST")
  when {
    value.isArrayOf<CharSequence>() -> {
      putCharSequenceArray(key, value as Array<out CharSequence>?)
    }
    value.isArrayOf<String>() -> putStringArray(key, value as Array<String>?)
    value.isArrayOf<Parcelable>() -> putParcelableArray(key, value as Array<Parcelable>?)
    else -> unsupported("Array type ${value.javaClass.canonicalName} is not supported")
  }
}

private fun Bundle.putArrayList(key: String, value: ArrayList<*>) {
  @Suppress("UNCHECKED_CAST")
  when (value.firstOrNull()) {
      is CharSequence -> putCharSequenceArrayList(key, value as ArrayList<CharSequence>?)
      is String -> putStringArrayList(key, value as ArrayList<String>?)
      is Parcelable -> putParcelableArrayList(key, value as ArrayList<out Parcelable>?)
      is Int, null -> putIntegerArrayList(key, value as ArrayList<Int>?)
    else -> unsupported("Type ${value.first().javaClass.canonicalName} in ArrayList is not supported")
  }
}

private fun Bundle.putSparseArrayOfParcelable(key: String, value: SparseArray<*>) {
  @Suppress("UNCHECKED_CAST")
  putSparseParcelableArray(key, value as SparseArray<out Parcelable>?)
}

/** @see [androidx.core.app.BundleCompat.putBinder] */
private val putIBinderMethod by lazy(LazyThreadSafetyMode.PUBLICATION) {
  Bundle::class.java.getMethod("putIBinder", String::class.java, IBinder::class.java).also {
    it.isAccessible = true
  }
}

/** @see [androidx.core.app.BundleCompat.putBinder] */
private fun Bundle.putBinderCompat(key: String, binder: IBinder) {
  putIBinderMethod.invoke(this, key, binder)
}