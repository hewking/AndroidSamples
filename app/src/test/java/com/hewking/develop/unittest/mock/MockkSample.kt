package com.hewking.develop.unittest.mock

import io.mockk.*
import junit.framework.Assert.assertEquals
import org.junit.Test

/**
 * @Description: mockk sample
 * @Author: jianhao
 * @Date:   2021/2/23 14:26
 * @License: Copyright Since 2020 Hive Box Technology. All rights reserved.
 * @Notice: This content is limited to the internal circulation of Hive Box, 
and it is prohibited to leak or used for other commercial purposes.
 */
class MockkSample {

    class Kid(private val mother: Mother) {
        var money = 0
            private set

        fun wantMoney() {
            mother.inform(money)
            money += mother.giveMoney()
        }
    }

    class Mother {

        fun inform(money: Int) {
            println("媽媽我現在有 $money 元，我要跟你拿錢！")
        }

        fun giveMoney(): Int {
            return 100
        }
    }

    @Test
    fun test(){
        val mother = mockk<Mother>()
        val kid = Kid(mother)
        every { mother.giveMoney() }returns 200

        kid.wantMoney()

        assertEquals(200, kid.money)

        every { mother.inform(any()) } just Runs

    }

    class Car {
        fun drive() = accelerate()

        private fun accelerate() = "going faster"
    }

    @Test
    fun testMock2(){
        val mock = spyk<Car>(recordPrivateCalls = true)

        every { mock["accelerate"]() } returns "going not so fast"

        assertEquals("going not so fast", mock.drive())

        verifySequence {
            mock.drive()
            mock["accelerate"]()
        }
    }



}