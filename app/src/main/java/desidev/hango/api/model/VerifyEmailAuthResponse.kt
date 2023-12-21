package desidev.hango.api.model

sealed interface VerifyEmailAuthResponse {
    data object Success : VerifyEmailAuthResponse
    data object AuthExpire: VerifyEmailAuthResponse
    data object InValidOtpValue: VerifyEmailAuthResponse
    data object LimitExceeds: VerifyEmailAuthResponse
}