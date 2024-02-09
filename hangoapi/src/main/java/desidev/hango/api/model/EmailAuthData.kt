package desidev.hango.api.model

import java.time.LocalDateTime
import java.time.ZoneOffset
import java.util.Date
import java.util.TimeZone


/**
 * Email Authentication Data
 *
 * The EmailAuthData data class encapsulates information about the current email authentication process.
 * It includes details such as the authentication ID, email address, authentication status, purpose,
 * expiration time, and creation time.
 *
 * ### Properties:
 *
 * - **authId:** Unique identifier for the email authentication process.
 *
 * - **email:** User's email address associated with the authentication.
 *
 * - **status:** Represents the status of the email authentication process. Possible values:
 *   - `VERIFIED`: Email has been successfully verified.
 *   - `OTP_EXP`: One-Time Password (OTP) has expired.
 *   - `NEED_VERIFICATION`: Email needs further verification.
 *   - `NO_ATTEMPT_LEFT`: No attempts left for verification.
 *
 * - **purpose:** Represents the purpose of the email authentication. Possible values:
 *   - `CREATE_ACCOUNT`: Authentication is for creating a new account.
 *   - `RESET_PASSWORD`: Authentication is for resetting the password.
 *
 * - **expireAt:** Date and time when the email authentication process is set to expire.
 *
 * - **createdAt:** Date and time when the email authentication process was created.
 *
 * ### Methods:
 *
 * - **isExpired(): Boolean:** Checks whether the email authentication has expired based on the current time.
 */
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

    enum class Purpose {
        CREATE_ACCOUNT, RESET_PASSWORD
    }

    /**
     * Checks whether the email authentication has expired based on the current time.
     *
     * @return True if the authentication has expired, false otherwise.
     */
    fun isExpired(): Boolean {
        val now = LocalDateTime.now(ZoneOffset.UTC)
        return expireAt.isBefore(now)
    }
}
