package desidev.hango.api.model

data class EmailAuthData(
    val authId: String,
    val genAt: Long,
    val expAt: Long,
    val status: Status,
    val purpose: Purpose
) {
    enum class Status {
        VERIFIED, OTP_EXP, NEED_VERIFICATION, NO_ATTEMPT_LEFT
    }

    enum class Purpose {
        CREATE_ACCOUNT, RESET_PASSWORD
    }
}
