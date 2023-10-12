package desidev.hango.viewmodels

import desidev.customnavigation.viewmodel.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

sealed class VerifyEmailState {
    data object EnterEmail : VerifyEmailState()
    data object EnterOtp : VerifyEmailState()
    data object SetPassword : VerifyEmailState()
}

class SignUpViewModel : ViewModel() {
    private val _state: MutableStateFlow<VerifyEmailState> = MutableStateFlow(VerifyEmailState.EnterEmail)
    var state : StateFlow<VerifyEmailState> =  _state

    fun sendOtpRequest(emailAddress: String) {
    }
}