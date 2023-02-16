package top.yuanning.rss_subscribe

import kotlinx.coroutines.*
import net.mamoe.mirai.console.plugin.jvm.JvmPluginDescription
import net.mamoe.mirai.console.plugin.jvm.KotlinPlugin
import net.mamoe.mirai.event.GlobalEventChannel
import net.mamoe.mirai.event.events.BotOnlineEvent
import net.mamoe.mirai.event.globalEventChannel
import top.yuanning.rss_subscribe.configs.RssConfig
import top.yuanning.rss_subscribe.datas.RssData
import top.yuanning.rss_subscribe.task.XmlCheckTask


object PluginMain : KotlinPlugin(
    JvmPluginDescription(
        id = "top.yuanning.rss_subscribe",
        name = "rss订阅更新插件",
        version = "0.3.0"
    ) {
        author("作者：源宁，联系方式：sxyn_749@163.com")
        info(
            """
            这是一个rss订阅更新插件，优先完成www.miobt.com的订阅更新功能
        """.trimIndent()
        )
        // author 和 info 可以删除.
    }
) {

    var job : Job? = null

    override fun onEnable() {
        RssConfig.reload()
        RssData.reload()

        val eventChannel = GlobalEventChannel.parentScope(this)
        eventChannel.subscribeOnce<BotOnlineEvent> {
            job = launch {
                XmlCheckTask(RssData.subscribes).main()
                while (this.isActive){
                    delay(RssConfig.checkTime)//订阅间隔的周期
                    XmlCheckTask(RssData.subscribes).main()
                }
            }
        }

    }

    override fun onDisable() {
        job?.cancel()
        super.onDisable()
    }

}
