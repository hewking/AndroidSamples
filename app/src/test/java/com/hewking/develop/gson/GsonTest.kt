package com.hewking.develop.gson

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonArray
import com.google.gson.JsonParser
import com.google.gson.reflect.TypeToken
import org.junit.Before
import org.junit.Test

/**
 * @author: jianhao
 * @create: 2020/8/14
 * @description:
 */
class GsonTest {

    lateinit var gson: Gson

    @Before
    fun init(){
        gson = GsonBuilder().create()
    }

    @Test
    fun test(){
        val p = Person()

        val map: Map<*, *> = gson.fromJson(gson.toJson(p), Map::class.java)

        println(map)
    }

    @Test
    fun foo(){
        val map = mapOf<String, String>("name" to "tom", "age" to "12")
        println(gson.fromJson(gson.toJson(map), Map::class.java))
    }

    @Test
    fun bar(){

        val typeToken = object :TypeToken<List<Person>>(){}.type

        val persons = listOf<Person>(Person(), Person())

        val json = gson.toJson(persons)

        val list = gson.fromJson<List<Person>>(json, List::class.java)

        println("list: $list")

        testJson<List<Person>>()
    }

    inline fun <reified T> testJson(){
        print(T::class.java)
//        gson.fromJson<T>("",T::class.java)
    }

    /**
     * @param json list的序列化字符串
     * @param <T>  T类型
     * @return List<T>
    </T></T> */
    fun <T> toList(json: String?, clazz: Class<T>?): List<T>? {
        return gson.fromJson(json, TypeToken.getParameterized(MutableList::class.java, clazz).type)
    }

// 或者

    // 或者
    /**
     * @param json list的序列化字符串
     * @param <T>  T类型
     * @return List<T>
    </T></T> */
    fun <T> parseList(json: String?, clazz: Class<T>?): List<T>? {
        val list: MutableList<T> = ArrayList()
        try {
            val array: JsonArray = JsonParser().parse(json).getAsJsonArray()
            for (elem in array) {
                list.add(Gson().fromJson(elem, clazz))
            }
        } catch (e: Exception) {
            return null
        }
        return list
    }

    data class CommonConfigEntity(
        val configCode: String = "",
        val configOrder: Int = 0,
        val id: Int = 0,
        val parentDesc: String = "",
        val subLink: String = "",
        var tagName: String = ""
    )

    @Test
    fun baz(){
        val json = "[{\"configCode\":\"accountsSecurity\",\"configOrder\":1,\"id\":19,\"parentDesc\":\"账户与安全\",\"subLink\":\"fcredirect://generalset_safe_account\",\"tagName\":\"\"},{\"configCode\":\"aboutUs\",\"configOrder\":2,\"id\":20,\"parentDesc\":\"关于我们\",\"subLink\":\"fcredirect://generalset_about_us\",\"tagName\":\"\"},{\"configCode\":\"userAgreement\",\"configOrder\":3,\"id\":21,\"parentDesc\":\"用户协议\",\"subLink\":\"fcredirect://generalset_user_protocol\",\"tagName\":\"\"},{\"configCode\":\"privacyPolicy\",\"configOrder\":4,\"id\":22,\"parentDesc\":\"隐私政策\",\"subLink\":\"fcredirect://generalset_privacy_policy\",\"tagName\":\"\"},{\"configCode\":\"clearCache\",\"configOrder\":5,\"id\":23,\"parentDesc\":\"清理缓存\",\"subLink\":\"fcredirect://generalset_clean_cache\",\"tagName\":\"30MB\"}]"

        val type = object : TypeToken<List<CommonConfigEntity>>(){}.type

        val result = gson.fromJson<List<CommonConfigEntity>>(json, type)

        println("result: $result")

    }

    @Test
    fun qur(){
        val json = "{\"configCode\":\"accountsSecurity\",\"configOrder\":1,\"id\":19,\"parentDesc\":\"账户与安全\",\"subLink\":\"fcredirect://generalset_safe_account\",\"tagName\":\"\"}"
        val result = gson.fromJson<Any>(json,Any::class.java)
        println("result $result")
    }

    inline fun <reified T> parse(json: String): List<T> {
        val type = TypeToken.getParameterized(List::class.java, T::class.java).type
        val gson = Gson()
        return gson.fromJson(json, type)
    }

    inline fun <reified T> parse2(json: String): List<T> {
        val type = object : TypeToken<List<T>>(){}.type
        return gson.fromJson(json, type)
    }


    data class Person(val name: String = "heihei") {
        var age = 13
        var set = 1
    }

}