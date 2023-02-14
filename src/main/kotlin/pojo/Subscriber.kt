package top.yuanning.rss_subscribe.pojo

import kotlinx.serialization.Serializable
import kotlin.properties.Delegates

@Serializable
class Subscriber {
    /**
     * friend，group
     */
    var type : String = ""
    var id : Long = 0
    var notifiedNumber: Int = 0

    constructor()
    constructor(type: String?, id: Long?) {
        this.type = type?:""
        this.id = id?:0
    }

    override fun toString(): String {
        return "Subscriber(type='$type', id=$id, notified=$notifiedNumber)"
    }


}