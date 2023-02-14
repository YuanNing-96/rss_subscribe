package top.yuanning.rss_subscribe.util

import net.mamoe.mirai.Bot
import net.mamoe.mirai.contact.Contact

object ContactUtil {


    /**
     * 从mcl登录的bot中，挑选有该订阅者好友/群的，建立Contact，并返回
     */
    fun getContactOrNull(type:String,id:Long): Contact? {
        var contact : Contact? = null

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
     * 从mcl登录的bot中，挑选有该订阅者好友/群的，建立Contact，并返回
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
}