package top.yuanning.rss_subscribe.util

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializer
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

/**
 * 这个object上面的两个注解不知道是干什么用的，反正先看看能不能用
 */
@OptIn(ExperimentalSerializationApi::class)
@Serializer(forClass = Date::class)
object DateSerializer: KSerializer<Date> {
    var dateFormatStr = "yyyy年MM月dd日-HH时mm分ss秒"
    private val df: DateFormat = SimpleDateFormat(dateFormatStr)

    override fun deserialize(decoder: Decoder): Date {
        return df.parse(decoder.decodeString())
    }

    override fun serialize(encoder: Encoder, value: Date) {
        encoder.encodeString(df.format(value))
    }
}