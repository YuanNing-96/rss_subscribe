package top.yuanning.rss_subscribe.configs

import net.mamoe.mirai.console.data.AutoSavePluginConfig
import net.mamoe.mirai.console.data.ValueDescription
import net.mamoe.mirai.console.data.value

object RssConfig : AutoSavePluginConfig("RssConfig") {

    @ValueDescription("订阅内容有更新时发送的消息的模板")
    val subscribeInfoSendMessage: String by value("订阅内容更新了")

    @ValueDescription("发送消息的周期，即发送消息后，经过该时间段后才会继续执行订阅任务，单位：毫秒")
    val sendMessageTime: Long by value(5000L)

    @ValueDescription("检测的周期，检测结束后，经过该时间段才会开始下一轮检测，单位：毫秒")
    val checkTime: Long by value(60000L)
}