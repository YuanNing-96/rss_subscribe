package top.yuanning.rss_subscribe.datas

import net.mamoe.mirai.console.data.AutoSavePluginData
import net.mamoe.mirai.console.data.value
import top.yuanning.rss_subscribe.pojo.Subscribe
import top.yuanning.rss_subscribe.pojo.Subscriber
import top.yuanning.rss_subscribe.util.ContactType

object RssData : AutoSavePluginData("RssData") {
    var subscribes : MutableSet<Subscribe> by value(
        mutableSetOf(Subscribe(
            name = "Lolihouse组的《别当欧尼酱》",
            targetUrl = "https://www.miobt.com/rss-%E5%88%AB%E5%BD%93%E6%AC%A7%E5%B0%BC%E9%85%B1+LoliHouse.xml",
            subscribers = mutableSetOf(Subscriber(ContactType.Friend,12346),Subscriber(ContactType.Group,45679))
    )))
}