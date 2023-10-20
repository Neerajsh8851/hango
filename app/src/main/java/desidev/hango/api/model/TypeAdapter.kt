package desidev.hango.api.model

import com.google.gson.*
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonWriter
import java.lang.reflect.Type
import java.time.LocalDate

class LocalDateTypeAdapter : TypeAdapter<LocalDate>() {
    override fun write(out: JsonWriter?, value: LocalDate?) {
        out?.value(value.toString())
    }

    override fun read(reader: JsonReader?): LocalDate {
        return LocalDate.parse(reader?.nextString())
    }
}

class SendOtpResponseTypeAdapter : JsonSerializer<VerifyResponse>,
    JsonDeserializer<VerifyResponse> {

    private val typeProperty = "type"
    private val successType = "success"
    private val failureType = "failure"
    override fun serialize(
        src: VerifyResponse?,
        typeOfSrc: Type?,
        context: JsonSerializationContext?
    ): JsonElement {
        val jsonObject = JsonObject()
        when (src!!) {
            is VerifyResponse.Success -> jsonObject.addProperty(typeProperty, successType)
            is VerifyResponse.Failure -> jsonObject.addProperty(typeProperty, failureType)
        }

        src.javaClass.declaredFields.forEach {
            it.isAccessible = true
            jsonObject.add(it.name, context?.serialize(it.get(src)))
        }

        return jsonObject
    }

    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): VerifyResponse {
        val jsonObject = json!!.asJsonObject

        return when (val type = jsonObject.get(typeProperty).asString) {
            successType -> context!!.deserialize(jsonObject, VerifyResponse.Success::class.java)
            failureType -> context!!.deserialize<VerifyResponse.Failure>(
                jsonObject,
                VerifyResponse.Failure::class.java
            )

            else -> throw JsonParseException("Unknown type $type")
        }
    }
}
