package desidev.hango.api.model

sealed class EmailAuthResponse {
    data class EmailAuth(
        val authId: String,
        val genAt: Long,
        val expAt: Long,
        val attemptLimit: Int,
    ) : EmailAuthResponse()

    data class Failure(val errorMessage: String) : EmailAuthResponse()
}