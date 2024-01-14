package desidev.hango.api.typeadapter

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonPrimitive
import com.google.gson.JsonSerializationContext
import com.google.gson.JsonSerializer
import java.lang.reflect.Type
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneOffset

class LocalDateTypeAdapter : JsonSerializer<LocalDate>, JsonDeserializer<LocalDate> {
    override fun serialize(src: LocalDate, typeOfSrc: Type?, context: JsonSerializationContext): JsonElement {
        return src.atStartOfDay().toInstant(ZoneOffset.UTC).toEpochMilli().let { JsonPrimitive(it) }
    }

    override fun deserialize(json: JsonElement, typeOfT: Type, context: JsonDeserializationContext): LocalDate {
        return LocalDate.ofInstant(Instant.ofEpochMilli(json.asLong), ZoneOffset.UTC)
    }
}