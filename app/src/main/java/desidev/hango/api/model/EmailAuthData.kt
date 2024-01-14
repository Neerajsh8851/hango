package desidev.hango.api.model

import java.time.LocalDateTime

data class EmailAuthData(
    val authId: String,
    val email: String,
    val status: Status,
    val purpose: Purpose,
    val expireAt: LocalDateTime,
    val createdAt: LocalDateTime,
) {

    enum class Status {
        VERIFIED, OTP_EXP, NEED_VERIFICATION, NO_ATTEMPT_LEFT
    }

    enum class Purpose { CREATE_ACCOUNT, RESET_PASSWORD }

    fun isExpired(): Boolean {
        val now = LocalDateTime.now()
        return expireAt.isBefore(now)
    }
}
