package top.yuanning.rss_subscribe.pojo


import kotlinx.serialization.Serializable
import top.yuanning.rss_subscribe.PluginMain

@Serializable
class Subscribe{

    var name:String = ""
    var targetUrl:String = ""
    var subscribers: MutableSet<Subscriber> = mutableSetOf()
    var init : Boolean = false
    var elementNumber:Int = 0

    constructor()
    constructor(name: String?, targetUrl: String?, subscribers: MutableSet<Subscriber>?) {
        this.name = name?:""
        this.targetUrl = targetUrl?:""
        this.subscribers = subscribers?: mutableSetOf()
    }

    override fun toString(): String {
        return "Subscribe(name='$name', targetUrl='$targetUrl', subscribers=$subscribers, init=$init, elementNumber=$elementNumber)"
    }


}