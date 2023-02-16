package top.yuanning.rss_subscribe.util

import kotlinx.coroutines.launch
import net.mamoe.mirai.Bot
import net.mamoe.mirai.contact.Contact
import net.mamoe.mirai.utils.warning
import top.yuanning.rss_subscribe.PluginMain
import top.yuanning.rss_subscribe.PluginMain.logger
import top.yuanning.rss_subscribe.configs.RssConfig

import top.yuanning.rss_subscribe.pojo.Subscriber
import top.yuanning.rss_subscribe.rss.Item

object ContactUtil {


    /**
     * 从mcl登录的bot中，挑选有该订阅者好友/群的，建立Contact，并返回。如果没有，返回null
     */
    fun getContactOrNull(type:ContactType, id:Long): Contact? {
        var contact : Contact?

        if(type.equals(ContactType.Friend)){
            Bot.instances.forEach {
                bot: Bot ->

                contact = bot.getFriend(id)
                if(contact == null){
                    return@forEach
                }else{
                    return contact
                }
            }
        }else if(type.equals(ContactType.Group)){
            Bot.instances.forEach {
                bot: Bot ->

                contact = bot.getGroup(id)
                if(contact!=null){
                    return@forEach
                }else{
                    return contact
                }
            }
        }else{
            return null
        }

        return null
    }

    /**
     * 从mcl登录的bot中，挑选有该订阅者好友/群的，建立Contact，并返回。如果没有，抛出异常
     */
    fun getContactOrThrow(type:ContactType, id:Long): Contact {

        var contact : Contact? = null

        if(type.equals(ContactType.Friend)){
            Bot.instances.forEach {
                contact = it.getFriend(id)
                if(contact == null){
                    return@forEach
                }
            }
        }else if(type.equals(ContactType.Group)){
            Bot.instances.forEach {
                contact = it.getGroup(id)
                if(contact!=null){
                    return@forEach
                }
            }
        }else{
            throw Exception("无该type类型，type只能有两种，friend或group")
        }
        return contact?:let { throw Exception("没有能联系上,type:${type.value},id:${id},的bot") }
    }

    fun sendMessageOfItemToSubscriber(item:Item,subscriber: Subscriber){
        var contact = getContactOrThrow(subscriber.type,subscriber.id)
        sendMessageOfItemToContact(item,contact)
    }

    fun sendMessageOfItemToContact(item: Item, contact: Contact){
        var messageTemplate = RssConfig.subscribeInfoSendMessage
        var message = DataParse.messageTemplateParse(messageTemplate,item)
        PluginMain.launch {
            logger.warning {
                message
            }
            contact.sendMessage(message)
        }
    }

    fun logMessageWithItemSubscriber(item:Item, subscriber: Subscriber){
        logger.warning{
            var messageTemplate = RssConfig.subscribeInfoSendMessage
            var message = DataParse.messageTemplateParse(messageTemplate,item)
            return@warning message
        }
    }
}