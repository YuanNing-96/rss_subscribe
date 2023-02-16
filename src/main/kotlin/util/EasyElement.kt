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

    @Deprecated("与java对接的接口，可能会有空指针异常", ReplaceWith("使用OrNull和OrThrow控制可空或抛出异常","getChildElementOrNull,getChildElementOrThrow"))
    fun getChildElement(childName: String):Element{
        return this.element.element(childName)
    }

    fun getChildElementOrNull(childName: String):Element?{
        return this.element.element(childName)
    }

    fun getChildElementOrThrow(childName: String):Element{
        return this.element.element(childName)?:throw Exception("element无该子节点")
    }

    fun getChildElements(childName: String):List<Element>{
        return this.element.elements(childName)
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

    fun isChannelElement():Boolean{
        return this.getName().equals("channel")
    }

    /**
     * 得到下一级某个字节点的文本
     */
    fun getChiledTextOrNull(childName:String):String?{
        try{
            return this.getChildElementOrNull(childName)?.text
        }catch (e:Exception){
            return null
        }
    }

    fun getChiledTextOrThrow(childName:String):String{
        try{
            return this.getChildElementOrThrow(childName).text
        }catch (e:Exception){
            throw Exception("无该子节点")
        }
    }

    fun getAvailableChildNames():List<String>{
        return this.element.elements().toSet().filterNotNull().map {
            element ->
            element.name
        }.toList()
    }
}