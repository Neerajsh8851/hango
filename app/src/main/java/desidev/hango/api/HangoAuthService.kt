package desidev.hango.api

import desidev.hango.api.model.BasicInfo
import desidev.hango.api.model.EmailAuthData
import desidev.hango.api.model.LoginResult
import desidev.hango.api.model.PictureData
import desidev.hango.api.model.UserCredential
import desidev.kotlin.utils.Option
import desidev.kotlin.utils.Result


interface HangoAuthService {
    suspend fun login(payload: UserCredential): Result<LoginResult, Exception>
    suspend fun registerNewAccount(
        verifiedAuthId: String,
        credential: UserCredential,
        userInfo: BasicInfo,
        pictureData: Option<PictureData>,
    ): Result<String, Exception>

    /**
     * Creates a new authentication data with the given email address and purpose.
     *
     * @param emailAddress The email address for which the authentication data is created.
     * @param purpose The purpose for which the authentication data is created.
     * @return An EmailAuthResponse object containing the newly created authentication data.
     */
    suspend fun requestEmailAuth(
        emailAddress: String,
        purpose: EmailAuthData.Purpose,
    ): Result<EmailAuthData, Exception>

    /**
     * Verifies the authentication data with the given OTP value and authentication ID.
     *
     * @param otpValue The OTP value provided by the client.
     * @param authId The authentication ID associated with the authentication data.
     * @return A VerifyEmailAuthResponse object indicating whether the verification was successful.
     */
    suspend fun verifyEmailAuth(
        authId: String,
        otpValue: String,
    ): Result<EmailAuthData, Exception>
}