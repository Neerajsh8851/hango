package desidev.hango.appstate

import desidev.hango.states.IAction


data class SignUpScreenState(
    val email: String = "",
    val isValid: Boolean = false,
    val isOtpSent: Boolean = false,
    val isOtpVerified: Boolean = false,
    val password: String = "",
    val uiState: UiState = UiState.EditEmail
)

enum class UiState {
    EditEmail,
    EnterOtp,
    SignUp
}


sealed class SignUpScreenStateAction : IAction<SignUpScreenState> {
    data class UpdateEmail(val email: String) : SignUpScreenStateAction() {
        override fun execute(prev: SignUpScreenState): SignUpScreenState {
            return prev.copy(email = email)
        }
    }


    data object SendOtp : SignUpScreenStateAction() {
        override fun execute(prev: SignUpScreenState): SignUpScreenState {
            return prev.copy(isOtpSent = true)
        }
    }
}


