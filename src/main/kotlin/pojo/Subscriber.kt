package top.yuanning.rss_subscribe.pojo

import kotlinx.serialization.Serializable
import kotlinx.serialization.Serializer
import top.yuanning.rss_subscribe.util.ContactType
import top.yuanning.rss_subscribe.util.ContactTypeSerializer
import top.yuanning.rss_subscribe.util.DateSerializer
import java.util.*

@Serializable
class Subscriber {

    @Serializable(with = ContactTypeSerializer::class)
    var type : ContactType = ContactType.Null

    var id : Long = 0

    @Serializable(with = DateSerializer::class)
    var notifideDate : Date = Date(0)

    constructor()
    constructor(type: ContactType?, id: Long?) {
        this.type = type?:ContactType.Null
        this.id = id?:0
    }

}