package desidev.hango.api

import desidev.hango.api.model.EmailAuthPurpose
import desidev.hango.api.model.EmailAuthResponse
import desidev.hango.api.model.RegisterAccountPayload
import desidev.hango.api.model.UserCredential
import desidev.hango.api.model.VerifyEmailAuthResponse


interface HangoAuthApi {
    suspend fun userSignIn(payload: UserCredential): Result<String>
    suspend fun registerNewAccount(payload: RegisterAccountPayload)
    suspend fun createEmailAuth(emailAddress: String, purpose: EmailAuthPurpose): EmailAuthResponse
    suspend fun verifyEmailAuth(otpValue: String, authId: String): VerifyEmailAuthResponse
}