package desidev.hango.ui.screens.signup.account

import com.arkivanov.decompose.value.Value
import desidev.hango.api.model.EmailAuthData
import desidev.kotlinutils.Option

interface AccountComponent {
    val userEmail: Value<String>
    val otpValue: Value<String>
    val authData: Value<Option<EmailAuthData>>

    val otpStatus: Value<OtpStatus>
    val accountCreateStatus: Value<AccountCreateStatus>
    fun setOtp(value: String)
    fun verifyOtp()
    fun requestEmailAuth()
    fun createAccount()

    sealed interface OtpStatus {
        data object BeforeSendingOtp : OtpStatus
        data object SendingOtp : OtpStatus
        data class OtpSent(val authData: EmailAuthData) : OtpStatus
        data class OtpSentFailed(val message: String) : OtpStatus
        data class VerifyingOtp(val authData: EmailAuthData) : OtpStatus
        data class OtpVerified(val authData: EmailAuthData) : OtpStatus
        data class OtpVerificationError(val authData: EmailAuthData, val message: String) : OtpStatus
        data class OtpInvalid(val authData: EmailAuthData) : OtpStatus
        data class NoAttemptsLeft(val authData: EmailAuthData) : OtpStatus
        data object OtpExpired : OtpStatus
    }

    sealed interface AccountCreateStatus {
        data object CreatingAccount : AccountCreateStatus
        data object AccountCreated : AccountCreateStatus
        data class AccountCreateFailed(val message: String) : AccountCreateStatus
    }
}


