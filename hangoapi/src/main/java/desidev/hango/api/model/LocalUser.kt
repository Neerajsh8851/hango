package desidev.hango.api.model

import java.time.LocalDateTime

/**
 * LocalUser represents the user that is currently logged in.
 */
data class LocalUser(
    val id: String,
    val name: String,
    val birthDate: LocalDateTime,
    val gender: String,
    val profilePic: String?,
    val email: String,
    val createdAt: LocalDateTime,
)
