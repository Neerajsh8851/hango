package desidev.hango.api.model

class PictureData(
    val data: ByteArray,
    val originalFilename: String,
    val type: Mimetype,
) {
    data class Mimetype(
        val contentType: String,
        val subType: String,
    ) {
        override fun toString(): String {
            return "$contentType/$subType"
        }
    }
}