package top.yuanning.rss_subscribe.util

import kotlinx.coroutines.launch
import net.mamoe.mirai.Bot
import net.mamoe.mirai.contact.Contact
import org.dom4j.Element
import top.yuanning.rss_subscribe.PluginMain
import top.yuanning.rss_subscribe.PluginMain.logger
import top.yuanning.rss_subscribe.configs.RssConfig

import top.yuanning.rss_subscribe.pojo.Subscriber

object ContactUtil {


    /**
     * 从mcl登录的bot中，挑选有该订阅者好友/群的，建立Contact，并返回。如果没有，返回null
     */
    fun getContactOrNull(type:String,id:Long): Contact? {
        var contact : Contact?

        if(type.equals("friend")){
            Bot.instances.forEach {
                contact = it.getFriend(id)
                if(contact!=null){
                    return contact
                }
            }
        }else if(type.equals("group")){
            Bot.instances.forEach {
                contact = it.getGroup(id)
                if(contact!=null){
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
    fun getContactOrThrow(type:String,id:Long): Contact {

        var contact : Contact? = null

        if(type.equals("friend")){
            Bot.instances.forEach {
                contact = it.getFriend(id)
                if(contact == null){
                    return@forEach
                }
            }
        }else if(type.equals("group")){
            Bot.instances.forEach {
                contact = it.getGroup(id)
                if(contact!=null){
                    return@forEach
                }
            }
        }else{
            throw Exception("无该type类型，type只能有两种，friend或group")
        }
        return contact?:let { throw Exception("没有能联系上,type:${type},id:${id},的bot") }
    }

    fun sendMessageOfItemToSubscriber(item:Element,subscriber: Subscriber){
        logger.info("content:${EasyElement(item).getItemTitle()}\nreceiver:$subscriber")
        var contact = getContactOrThrow(subscriber.type,subscriber.id)
        sendMessageOfItemToContact(item,contact)
    }

    fun sendMessageOfItemToContact(item: Element,contact: Contact){
        var messageTemplate = RssConfig.subscribeInfoSendMessage//TODO 这里需要对messageTemplate进行处理
        var message = DataParse.messageTemplateParse(messageTemplate,item)
        PluginMain.launch {
            contact.sendMessage(message)
        }
    }
}