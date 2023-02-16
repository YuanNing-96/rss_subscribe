package top.yuanning.rss_subscribe.pojo

import org.junit.Assert
import org.junit.Test
import java.util.*

class SubscribeTest {

    @Test
    fun initializeWithDate(){
        var subscribe = Subscribe()
        Assert.assertEquals(false,subscribe.initialized)
        Assert.assertEquals(Date(0),subscribe.minNotifideDate)
        subscribe.subscribers.forEach {
                subscriber: Subscriber ->
            Assert.assertEquals(Date(0),subscriber.notifideDate)
        }

        val date = Date()
        subscribe.initialize(date)

        Assert.assertEquals(true,subscribe.initialized)
        Assert.assertEquals(date,subscribe.minNotifideDate)
        subscribe.subscribers.forEach {
            subscriber: Subscriber ->
            Assert.assertEquals(date,subscriber.notifideDate)
        }
    }
}