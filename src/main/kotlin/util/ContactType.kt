package top.yuanning.rss_subscribe.util

import kotlinx.serialization.Serializable

@Serializable
enum class ContactType(val value:String) {
    Null(""),
    Group("group"),
    Friend("friend");

}