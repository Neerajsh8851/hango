package desidev.hango.api.model

import io.ktor.http.ContentType

class PictureData(
    val data: ByteArray,
    val originalFilename: String,
    val type: ContentType,
)
