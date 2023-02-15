package top.yuanning.rss_subscribe.pojo

import kotlinx.serialization.Serializable
import top.yuanning.rss_subscribe.util.DateSerializer
import java.util.*

@Serializable
class Subscriber {
    /**
     * friend，group
     */
    var type : String = ""
    var id : Long = 0

    @Serializable(with = DateSerializer::class)
    var notifideDate : Date = Date(0)

    constructor()
    constructor(type: String?, id: Long?) {
        this.type = type?:""
        this.id = id?:0
    }

    override fun toString(): String {
        return "Subscriber(type='$type', id=$id, notifideDate=$notifideDate)"
    }


}