package top.yuanning.rss_subscribe.util

import kotlinx.serialization.KSerializer
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.Serializer
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import java.util.*

@OptIn(ExperimentalSerializationApi::class)
@Serializer(forClass = ContactType::class)
object ContactTypeSerializer : KSerializer<ContactType> {
    override fun deserialize(decoder: Decoder): ContactType {
        return ContactType.Null.valueOf(decoder.decodeString())
    }

    override fun serialize(encoder: Encoder, value: ContactType) {
        encoder.encodeString(value.value)
    }
}