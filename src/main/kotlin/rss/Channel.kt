package top.yuanning.rss_subscribe.rss

import org.dom4j.Element
import top.yuanning.rss_subscribe.util.EasyElement

class Channel {

    lateinit var element: Element

    var title: String
    var link: String
    var description: String

    lateinit var items: List<Item>

    var language: String? = null
    var copyright: String? = null
    var managingEditor: String? = null
    var webMaster: String? = null
    var pubDate: String? = null
    var lastBuildDate: String? = null
    var category: String? = null
    var generator: String? = null
    var docs: String? = null
    var ttl: String? = null
    var image: String? = null
    var rating: String? = null
    var skipHours: String? = null
    var skipDays: String? = null

    @Deprecated("看起来就很难用的样子，而且真的没用")
    private constructor(title: String, link: String, description: String) {
        this.title = title
        this.link = link
        this.description = description
    }

    @Deprecated("看起来就很难用的样子，而且真的没用")
    private constructor(
        title: String, link: String, description: String,
        language: String?, copyright: String?, managingEditor: String?,
        webMaster: String?, pubDate: String?, lastBuildDate: String?,
        category: String?, generator: String?, docs: String?,
        ttl: String?, image: String?, rating: String?,
        skipHours: String?, skipDays: String?
    ) {
        this.title = title
        this.link = link
        this.description = description

        this.language = language
        this.copyright = copyright
        this.managingEditor = managingEditor
        this.webMaster = webMaster
        this.pubDate = pubDate
        this.lastBuildDate = lastBuildDate
        this.category = category
        this.generator = generator
        this.docs = docs
        this.ttl = ttl
        this.image = image
        this.rating = rating
        this.skipHours = skipHours
        this.skipDays = skipDays
    }

    constructor(element: Element){
        val channel = EasyElement(element)
        if(!channel.isChannelElement()){
            throw Exception("当前节点不是channel节点")
        }else{
            this.element = element

            this.title = channel.getChiledTextOrThrow(ChannelAttributes.Title.value)
            this.link = channel.getChiledTextOrThrow(ChannelAttributes.Link.value)
            this.description = channel.getChiledTextOrThrow(ChannelAttributes.Description.value)

            this.items = channel.getChildElements("item").map{
                elementFromList: Element ->

                Item(elementFromList)
            }.toList()

            this.language = channel.getChiledTextOrNull(ChannelAttributes.Language.value)
            this.copyright = channel.getChiledTextOrNull(ChannelAttributes.Copyright.value)
            this.managingEditor = channel.getChiledTextOrNull(ChannelAttributes.ManagingEditor.value)
            this.webMaster = channel.getChiledTextOrNull(ChannelAttributes.WebMaster.value)
            this.pubDate = channel.getChiledTextOrNull(ChannelAttributes.PubDate.value)
            this.lastBuildDate = channel.getChiledTextOrNull(ChannelAttributes.LastBuildDate.value)
            this.category = channel.getChiledTextOrNull(ChannelAttributes.Category.value)
            this.generator = channel.getChiledTextOrNull(ChannelAttributes.GENERATOR.value)
            this.docs = channel.getChiledTextOrNull(ChannelAttributes.DOCS.value)
            this.ttl = channel.getChiledTextOrNull(ChannelAttributes.TTL.value)
            this.image = channel.getChiledTextOrNull(ChannelAttributes.IMAGE.value)
            this.rating = channel.getChiledTextOrNull(ChannelAttributes.RATING.value)
            this.skipHours = channel.getChiledTextOrNull(ChannelAttributes.SKIPHOURS.value)
            this.skipDays = channel.getChiledTextOrNull(ChannelAttributes.SKIPDAYS.value)
        }
    }

    fun getAvailableChildNames():List<String>{
        return EasyElement(this.element).getAvailableChildNames()
    }


}