package desidev.hango.api.model

import java.time.LocalDateTime

data class User(
    val id: String,
    val name: String,
    val dateOfBirth: LocalDateTime,
    val gender: String,
    val profilePic: String?,
    val email: String,
    val createdAt: LocalDateTime,
    val status: Status
) {
    enum class Status {
        ACTIVE, INACTIVE, BUSY
    }
}