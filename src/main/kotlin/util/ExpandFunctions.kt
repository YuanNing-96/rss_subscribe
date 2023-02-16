package top.yuanning.rss_subscribe.util

/**
 * 如果原字符串为null，则返回空字符串，否则返回其本身
 */
fun String?.getStringOrEmpty():String{
    if (this == null){
        return ""
    }else{
        return this
    }
}

fun ContactType.valueOf(value: String):ContactType{
    return when(value){
        "" -> ContactType.Null
        "group" -> ContactType.Group
        "friend" -> ContactType.Friend
        else -> ContactType.Null
    }
}
