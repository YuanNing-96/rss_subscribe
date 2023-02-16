package top.yuanning.rss_subscribe.rss

import org.dom4j.Element
import top.yuanning.rss_subscribe.util.EasyElement

class Rss {
    var channel : Channel

    var element: Element

    constructor(root: Element){
        this.element = root
        this.channel = Channel(EasyElement(root).getChildElementOrThrow("channel"))
    }

}