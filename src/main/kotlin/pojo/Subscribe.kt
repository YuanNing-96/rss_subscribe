package top.yuanning.rss_subscribe.pojo


import kotlinx.serialization.Serializable
import top.yuanning.rss_subscribe.util.DateSerializer
import java.util.*

@Serializable
class Subscribe{

    var name:String = ""
    var targetUrl:String = ""
    var subscribers: MutableSet<Subscriber> = mutableSetOf()
    var initialized : Boolean = false

    @Serializable(with = DateSerializer::class)
    var minNotifideDate : Date = Date(0)

    constructor()
    constructor(name: String?, targetUrl: String?, subscribers: MutableSet<Subscriber>?) {
        this.name = name?:""
        this.targetUrl = targetUrl?:""
        this.subscribers = subscribers?: mutableSetOf()
    }

    override fun toString(): String {
        return "Subscribe(name='$name', targetUrl='$targetUrl', subscribers=$subscribers, initialized=$initialized, minNotifideDate=$minNotifideDate)"
    }
    
    fun initialize(date: Date){
        this.subscribers.forEach { 
            it.notifideDate = date
        }
        this.minNotifideDate = date
        this.initialized = true
    }

    fun initialize(timeStamp: Long){
        val date = Date(timeStamp)
        initialize(date)
    }


}