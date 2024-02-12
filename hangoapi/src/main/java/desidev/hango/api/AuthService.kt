package desidev.hango.api

import desidev.hango.api.model.BasicInfo
import desidev.hango.api.model.EmailAuthData
import desidev.hango.api.model.PictureData
import desidev.hango.api.model.SessionInfo
import desidev.hango.api.model.UserCredential
import desidev.kotlinutils.Option
import desidev.kotlinutils.Result


interface AuthService {
    suspend fun login(payload: UserCredential): Result<SessionInfo, LoginError>

    suspend fun registerNewAccount(
        verifiedAuthId: String,
        credential: UserCredential,
        userInfo: BasicInfo,
        pictureData: Option<PictureData>,
    ): Result<SessionInfo, Exception>


    /**
     * Retrieves the current login session.
     */
    suspend fun getCurrentLoginSession(): Option<SessionInfo>

    /**
     * Logs out the current user.
     */
    suspend fun logout()

    /**
     * Creates a new authentication data with the given email address and purpose.
     *
     * @param emailAddress The email address for which the authentication data is created.
     * @param purpose The purpose for which the authentication data is created.
     *
     */
    suspend fun requestEmailAuth(
        emailAddress: String,
        purpose: EmailAuthData.Purpose,
    ): Result<EmailAuthData, EmailAuthFailure>

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
    ): Result<EmailAuthData, EmailAuthFailure>
}