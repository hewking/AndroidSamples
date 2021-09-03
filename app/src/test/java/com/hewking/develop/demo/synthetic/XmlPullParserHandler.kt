package com.hewking.develop.demo.synthetic

import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserException
import org.xmlpull.v1.XmlPullParserFactory
import java.io.IOException
import java.io.InputStream

/**
 *@Description:
 *@Author: jianhao
 *@Date:   2021-09-03 15:30
 *@License: Copyright Since 2020 Hive Box Technology. All rights reserved.
 *@Notice: This content is limited to the internal circulation of
 *  Hive Box, and it is prohibited to leak or used for other commercial purposes.
 */
class XmlPullParserHandler {

    private val ids = ArrayList<String>()
    private var text: String? = null

    fun parse(inputStream: InputStream): List<String> {
        try {
            val factory = XmlPullParserFactory.newInstance()
            factory.isNamespaceAware = true
            val parser = factory.newPullParser()
            parser.setInput(inputStream, null)
            var eventType = parser.eventType
            while (eventType != XmlPullParser.END_DOCUMENT) {
                val tagname = parser.name
                when (eventType) {
                    XmlPullParser.START_TAG -> if (tagname.equals("employee", ignoreCase = true)) {
                        // create a new instance of employee
                    }
                    XmlPullParser.TEXT -> text = parser.text
                    XmlPullParser.END_TAG -> if (tagname.equals("android:id", ignoreCase = true)) {
                        ids.add(text.orEmpty())
                    }

                    else -> {
                    }
                }
                eventType = parser.next()
            }

        } catch (e: XmlPullParserException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return ids
    }

}