package com.hewking.develop.util


/**
 *@Description:  util  金额显示转换
 *@Author:  luobin
 *@Date: 2020/11/1 19:59
 *@License: Copyright Since 2020 Hive Box Technology. All rights reserved.
 *@Notice:  This content is limited to the internal  circulation of Hive Box, and it is prohibited to leak or used for other commercial purposes.
 */
object UnitUtil {


  //千分符
  fun addThousandSeparator(text: String): String {
    val index = text.indexOf(".")
    return if (index > 0) {
      val integerPartial = text.substring(0, index)
      val decimalPartial = text.substring(index)
      addThousandSeparatorForInteger(integerPartial).toString() + decimalPartial
    } else {
      addThousandSeparatorForInteger(text)
    }
  }

  // 只给整数加千分位分隔符
  private fun addThousandSeparatorForInteger(text: String): String {
    val index = text.indexOf(".")
    return if (index != -1) {
      text
    } else {
      var length = text.length
      val stringContainer = ArrayList<String>()
      while (length > 3) {
        stringContainer.add(text.substring(length - 3, length))
        length -= 3
      }
      stringContainer.add(text.substring(0, length)) // 将最前面的小于三个数字的也加入到数组去
      val buffer = StringBuffer()
      for (i in stringContainer.size - 1 downTo 0) {
        buffer.append(stringContainer[i] + ",")
      }
      buffer.deleteCharAt(buffer.length - 1)
      buffer.toString()
    }
  }


}