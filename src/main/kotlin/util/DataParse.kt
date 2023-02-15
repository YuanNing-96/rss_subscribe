package top.yuanning.rss_subscribe.util

import org.dom4j.Element
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

object DataParse {

    /**
     * 用于解析rss数据中的pubDate，返回Date类型的实例
     *
     * 当数据格式错误时，会抛出异常，样例如下：
     *
     * java.text.ParseException: Unparseable date: ""
     */
    fun pubDateParseToDate(dateStr:String):Date{
        val sdfTemp = SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss z", Locale.US);
        val aZone = SimpleTimeZone(8,"GMT");
        sdfTemp.timeZone = aZone
        return sdfTemp.parse(dateStr)
    }

    fun dateToString(date:Date):String{
        var dateFormatStr = "yyyy年MM月dd日-HH时mm分ss秒"
        val df: DateFormat = SimpleDateFormat(dateFormatStr)
        return df.format(date)
    }

    /**
     * 用于解析rss数据中的pubDate，返回时间戳
     *
     * 当数据格式错误时，会抛出异常，样例如下：
     *
     * java.text.ParseException: Unparseable date: ""
     */
    fun pubDateParseToTimestamp(dateStr:String):Long{
        val sdfTemp = SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss z", Locale.US);
        val aZone = SimpleTimeZone(8,"GMT");
        sdfTemp.timeZone = aZone
        return sdfTemp.parse(dateStr).time
    }

    fun messageTemplateParse(messageTemplate:String,item:Element):String{
        var easyItem = EasyElement(item)
        val message = messageTemplate
            .replace("${'$'}{title}",easyItem.getItemTitle())
            .replace("${'$'}{link}",easyItem.getItemLink())
            .replace("${'$'}{description}",easyItem.getItemDescription())
            .replace("${'$'}{pubDate}", dateToString(easyItem.getItemPubDate()))
            .replace("${'$'}{category}",easyItem.getItemCategory())

        return message
    }
}