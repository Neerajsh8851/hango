package desidev.hango.api.model
data class EmailAuthData(
    val id: String,
    val email: String,
    val status: Status,
    val purpose: Purpose,
    val expAfter: Long,
    val genAt: Long,
) {

    enum class Status {
        VERIFIED, OTP_EXP, NEED_VERIFICATION, NO_ATTEMPT_LEFT
    }

    enum class Purpose { CREATE_ACCOUNT, RESET_PASSWORD }

    fun isAuthDataExpired(): Boolean = System.currentTimeMillis() > expAfter
    fun isVerified(): Boolean = status == Status.VERIFIED
}
