package top.yuanning.rss_subscribe.util

import org.dom4j.Element
import top.yuanning.rss_subscribe.rss.Item
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

//    fun pubDateParseToDate(dateStr:String?):Date?{
//        if(dateStr == null){
//            return null
//        }else{
////            return pubDateParseToDate(dateStr)
//            val sdfTemp = SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss z", Locale.US);
//            val aZone = SimpleTimeZone(8,"GMT");
//            sdfTemp.timeZone = aZone
//            return sdfTemp.parse(dateStr)
//        }
//    } TODO 往后版本中，pubDate可为空时，使用这行代码

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

    /**
     * TODO 目前内容的映射只做了item的title，link，description，pubDate四个子属性
     */
    fun messageTemplateParse(messageTemplate:String,item:Item):String{

        val avaliableChildNames = item.getAvailableChildNames()

        val message = messageTemplate
            .replace("${'$'}{title}",item.title)
            .replace("${'$'}{link}",item.link)
            .replace("${'$'}{description}",item.description)
            .replace("${'$'}{pubDate}", this.dateToString(item.pubDate))

        return message
    }


}

