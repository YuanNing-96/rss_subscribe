package top.yuanning.rss_subscribe.kotlinApi

import org.junit.Assert
import org.junit.Test
import top.yuanning.rss_subscribe.util.DataParse
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class TimeParseTest {

    @Test
    fun timeParseTest_Normal(){
        val sdfTemp = SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        var time = "Tue, 14 Feb 2023 14:58:20 +0800"
        var date = DataParse.pubDateParseToDate(time)
        var dateActual = sdfTemp.parse("2023-02-14 14:58:20")
        Assert.assertEquals(date,dateActual)
    }

    @Test
    fun timeParseTest_EmptyString(){
        var time = ""
        Assert.assertThrows(ParseException::class.java){
            DataParse.pubDateParseToDate(time)
        }
    }

    @Test
    fun timeStampToDateTest(){
        val date1 = Date(1674357900000)
        val sdfTemp = SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        var date2 = sdfTemp.parse("2023-01-22 11:25:00")
        Assert.assertEquals(date1,date2)
    }

}