package desidev.hango.api

import desidev.hango.api.model.EmailAuthPurpose
import desidev.hango.api.model.EmailAuthResponse
import desidev.hango.api.model.RegisterAccountPayload
import desidev.hango.api.model.UserCredential
import desidev.hango.api.model.VerifyEmailAuthResponse
import desidev.kotlin.utils.Result


interface HangoAuthApi {
    suspend fun userSignIn(payload: UserCredential): Result<String, Exception>
    suspend fun registerNewAccount(payload: RegisterAccountPayload, authDataId: String): Result<Nothing, String>

    /**
     * Creates a new authentication data with the given email address and purpose.
     *
     * @param emailAddress The email address for which the authentication data is created.
     * @param purpose The purpose for which the authentication data is created.
     * @return An EmailAuthResponse object containing the newly created authentication data.
     */
    suspend fun createEmailAuth(emailAddress: String, purpose: EmailAuthPurpose): EmailAuthResponse

    /**
     * Verifies the authentication data with the given OTP value and authentication ID.
     *
     * @param otpValue The OTP value provided by the client.
     * @param authId The authentication ID associated with the authentication data.
     * @return A VerifyEmailAuthResponse object indicating whether the verification was successful.
     */
    suspend fun verifyEmailAuth(otpValue: String, authId: String): VerifyEmailAuthResponse
}