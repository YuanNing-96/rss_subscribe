package top.yuanning.rss_subscribe.kotlinApi

import org.junit.Assert
import org.junit.Test

class ListReverseTest {

    /**
     * 测试列表倒转函数
     */
    @Test
    fun listAsReversedTest(){
        var a = listOf<Int>(1,2,3)
        var b = a.asReversed()

        Assert.assertEquals(b, listOf(3,2,1))
    }
}