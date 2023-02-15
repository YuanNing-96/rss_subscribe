package top.yuanning.rss_subscribe.util

import org.dom4j.Element
import top.yuanning.rss_subscribe.pojo.Subscribe
import top.yuanning.rss_subscribe.pojo.Subscriber

object RssUpdateCheck {

    /**
     * 检查一组subscribe是否需要更新，
     * 需要更新则返回true，无需更新返回false
     * 如果subscribe的date比最新item的date还要新，则返回false
     */
    fun checkSubscribeUpdate(item:Element,subscribe: Subscribe):Boolean{
        val itemDate = EasyElement(item).getItemPubDate()
        val subscribeDate = subscribe.minNotifideDate
        if(itemDate.compareTo(subscribeDate) == 0){
            return false
        }else if(itemDate.compareTo(subscribeDate) > 0){
            return true
        }else{
            return false
        }
    }

    /**
     * 检查一组subscribe是否需要更新，
     * 需要更新则返回true，无需更新返回false
     * 如果subscribe的date比最新item的date还要新，则抛出异常
     */
    fun checkSubscribeUpdateOrThrow(item:Element,subscribe: Subscribe):Boolean{
        val itemDate = EasyElement(item).getItemPubDate()
        val subscribeDate = subscribe.minNotifideDate
        if(itemDate.compareTo(subscribeDate) == 0){
            return false
        }else if(itemDate.compareTo(subscribeDate) > 0){
            return true
        }else{
            throw Exception("subscribe的date比最新item的date还要新")
        }
    }

    /**
     * 检查一个subscriber是否需要更新，
     * 需要更新则返回true，无需更新返回false
     * 如果subscriber的date比最新item的date还要新，则返回false
     */
    fun checkSubscriberUpdate(item: Element,subscriber: Subscriber):Boolean{
        val itemDate = EasyElement(item).getItemPubDate()
        val subscriberDate = subscriber.notifideDate
        if(itemDate.compareTo(subscriberDate) == 0){
            return false
        }else if(itemDate.compareTo(subscriberDate) > 0){
            return true
        }else{
            return false
        }
    }

    /**
     * 检查一个subscriber是否需要更新，
     * 需要更新则返回true，无需更新返回false
     * 如果subscriber的date比最新item的date还要新，则抛出异常
     */
    fun checkSubscriberUpdateOrThrow(item: Element,subscriber: Subscriber):Boolean{
        val itemDate = EasyElement(item).getItemPubDate()
        val subscriberDate = subscriber.notifideDate
        if(itemDate.compareTo(subscriberDate) == 0){
            return false
        }else if(itemDate.compareTo(subscriberDate) > 0){
            return true
        }else{
            throw Exception("subscriber的date比最新item的date还要新")
        }
    }
}