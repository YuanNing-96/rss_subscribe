package top.yuanning.rss_subscribe.rss

/**
 * 这里的属性应该是rss v2.0规范中所有可能出现的item节点的属性
 */
enum class ItemAttributes(val value:String) {
    TITLE("title"),
    LINK("link"),
    DESCRIPTION("description"),
    AUTHOR("author"),
    CATEGORY("category"),
    COMMENTS("comments"),
    ENCLOSURE("enclosure"),
    GUID("guid"),
    PUBDATE("pubDate"),
    SOURCE("source"),
}