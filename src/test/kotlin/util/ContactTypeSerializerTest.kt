package top.yuanning.rss_subscribe.util

import org.junit.Assert
import org.junit.Test
import top.yuanning.rss_subscribe.util.ContactType
import top.yuanning.rss_subscribe.util.valueOf

class ContactTypeSerializerTest {

    @Test
    fun deserializeTest(){
        Assert.assertEquals(ContactType.Friend,ContactType.Null.valueOf("friend"))
    }

    @Test
    fun serializeTest(){
        Assert.assertEquals("friend",ContactType.Friend.value)
    }
}