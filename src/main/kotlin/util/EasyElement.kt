package top.yuanning.rss_subscribe.util

import org.dom4j.Element
import java.util.*

/**
 * 用于对org.dom4j.Element进行处理，同时进行注释，防止忘记函数功能
 */
class EasyElement(element: Element) {

    var element : Element;

    init {
        this.element = element
    }

    /**
     * 得到节点自身的标签，
     *
     * 如rss,title等
     */
    fun getName():String?{
        return this.element.name
    }

    /**
     * 得到节点自身的文本内容，
     *
     * 如:[SweetSub&amp;LoliHouse] 不当哥哥了！/ Oniichan ha Oshimai! - 01 [WebRip 1080p HEVC-10bit AAC][简繁日内封字幕]（检索用：别当欧尼酱了！）
     */
    fun getText():String?{
        return this.element.text
    }

    /**
     * 为了适配非miobt链接设计的通用得到子节点的函数
     */
    fun getChileElement(list: List<String>):Element{
        var childElement = this.element
        list.forEach {
            childElement = childElement.element(it)
        }
        return childElement
    }

    /**
     * 为了适配非miobt链接设计的通用得到子节点的函数
     */
    fun getChileElements(list: List<String>):List<Element>{
        var childElement = this.element
        var childElements : MutableList<Element> = mutableListOf()
        for ((index, s) in list.withIndex()) {
            if(index == list.size-1){
                childElements = childElement.elements(s)
            }else{
                childElement = childElement.element(s)
            }
        }
        return childElements.toList()
    }

    fun isItemElement():Boolean{
        return this.getName().equals("item")
    }

    /**
     * 获取item节点的title
     */
    fun getItemTitle():String{
        if (!this.isItemElement()){
            throw Exception("非item节点，无法获取item节点的title")
        }else{
            val title = this.getChileElement(listOf("title")).text
            return title
        }
    }

    /**
     * 获取item节点的link
     */
    fun getItemLink():String{
        if (!this.isItemElement()){
            throw Exception("非item节点，无法获取item节点的link")
        }else{
            val link = this.getChileElement(listOf("link")).text
            return link
        }
    }

    /**
     * 获取item节点的description
     */
    fun getItemDescription():String{
        if (!this.isItemElement()){
            throw Exception("非item节点，无法获取item节点的description")
        }else{
            val description = this.getChileElement(listOf("description")).text
            return description
        }
    }

    /**
     * 获取item节点的guid
     */
    fun getItemGuid():String{
        if (!this.isItemElement()){
            throw Exception("非item节点，无法获取item节点的guid")
        }else{
            val guid = this.getChileElement(listOf("guid")).text
            return guid
        }
    }

    /**
     * 获取item节点的author
     */
    fun getItemAuthor():String{
        if (!this.isItemElement()){
            throw Exception("非item节点，无法获取item节点的author")
        }else{
            val author = this.getChileElement(listOf("author")).text
            return author
        }
    }

    /**
     * 获取item节点的enclosure
     */
    fun getItemEnclosure():String{
        if (!this.isItemElement()){
            throw Exception("非item节点，无法获取item节点的enclosure")
        }else{
            val enclosure = this.getChileElement(listOf("enclosure")).text
            return enclosure
        }
    }

    /**
     * 获取该节点的pubDate，如果不是item节点，则没有pubDate属性，会抛出异常
     */
    fun getItemPubDate(): Date {
        if (!this.isItemElement()){
            throw Exception("非item节点，无法获取pubDate")
        }else{
            val date = DataParse.pubDateParseToDate(this.getChileElement(listOf("pubDate")).text)
            return date
        }
    }

    /**
     * 获取item节点的category
     */
    fun getItemCategory():String{
        if (!this.isItemElement()){
            throw Exception("非item节点，无法获取item节点的category")
        }else{
            val category = this.getChileElement(listOf("category")).text
            return category
        }
    }
}