package top.yuanning.rss_subscribe.util

import org.junit.Assert
import org.junit.Test

class ExpandFunctionsTest {

    @Test
    fun ContactType_valueOf_test(){
        Assert.assertEquals(ContactType.Friend,ContactType.Null.valueOf("friend"))
    }

    @Test
    fun getStringOrEmpty_test(){
        val test1 : String? = null
        Assert.assertEquals("",test1.getStringOrEmpty())
        val test2 = "1234"
        Assert.assertEquals("1234",test2)
    }
}