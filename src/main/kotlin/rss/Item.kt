package top.yuanning.rss_subscribe.rss

import org.dom4j.Element
import top.yuanning.rss_subscribe.util.DataParse
import top.yuanning.rss_subscribe.util.EasyElement
import java.util.*

class Item {

    var element: Element

    var title: String
    var link: String
    var description: String

    var author: String? = null
    var category: String? = null
    var comments: String? = null
    var enclosure: String? = null
    var guid: String? = null
//    var pubDate: Date? = null
    var pubDate: Date
    var source: String? = null

    constructor(element : Element){

        val item = EasyElement(element)

        if(!item.isItemElement()){
            throw Exception("该节点不是item节点")
        }else{
            this.element = element

            this.title = item.getChiledTextOrThrow(ItemAttributes.TITLE.value)
            this.link = item.getChiledTextOrThrow(ItemAttributes.LINK.value)
            this.description = item.getChiledTextOrThrow(ItemAttributes.DESCRIPTION.value)

            this.author = item.getChiledTextOrNull(ItemAttributes.AUTHOR.value)
            this.category = item.getChiledTextOrNull(ItemAttributes.CATEGORY.value)
            this.comments = item.getChiledTextOrNull(ItemAttributes.COMMENTS.value)
            this.enclosure = item.getChiledTextOrNull(ItemAttributes.ENCLOSURE.value)
            this.guid = item.getChiledTextOrNull(ItemAttributes.GUID.value)
            this.pubDate = DataParse.pubDateParseToDate(item.getChiledTextOrNull(ItemAttributes.PUBDATE.value)
                ?: throw Exception("目前版本，订阅的item必须含有pubDate属性，否则无法检测更新"))
            this.source = item.getChiledTextOrNull(ItemAttributes.SOURCE.value)
        }
    }

    fun getAvailableChildNames():List<String>{
        return EasyElement(this.element).getAvailableChildNames()
    }
}