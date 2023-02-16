package top.yuanning.rss_subscribe.rss

/**
 * 这里的属性应该是rss v2.0规范中所有可能出现的channel节点的属性
 */
enum class ChannelAttributes(val value:String) {
    Title("title"),
    Link("link"),
    Description("description"),
    Language("language"),
    Copyright("copyright"),
    ManagingEditor("managingEditor"),
    WebMaster("webMaster"),
    PubDate("pubDate"),
    LastBuildDate("lastBuildDate"),
    Category("category"),
    GENERATOR("generator"),
    DOCS("docs"),
    TTL("ttl"),
    IMAGE("image"),
    RATING("rating"),
    SKIPHOURS("skipHours"),
    SKIPDAYS("skipDays"),
    Item("item")
}