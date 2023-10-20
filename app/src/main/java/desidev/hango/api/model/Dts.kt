package desidev.hango.api.model

/**
 * This file defines the data transfer classes / payload classes
 * for the communication between the server and the client application
 */

data class VerifyStartPayload(val email: String)

sealed class VerifyResponse {
    data class Success(
        val tok: String,
        val genAt: Long,
        val expAt: Long,
        val attemptLimit: Int,
    ) : VerifyResponse()

    data class Failure(val errorMessage: String) : VerifyResponse()
}

data class VerifyOtpPayload(
    val tok: String,
    val otp: String,
)

data class VerifyTokenResponse(
    val tok: String,
    val isVerified: Boolean,
    val msg: String?
)


data class VerifyRetryPayload(val tok: String)
data class VerifyRetryResponse(
    val tok: String,
    val genAt: Long,
    val expAt: Long,
    val attemptLimit: Int,
    val msg: String?
)

data class UserSignUp(val verifiedTok: String, val password: String)
data class UserCredentials(val email: String, val password: String)
sealed class JwtToken(val jwt: Boolean)