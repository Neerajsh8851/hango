package desidev.hango.ui.screens.signup_process.auth

import com.arkivanov.decompose.ComponentContext
import desidev.hango.ui.screens.Events
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow


sealed interface Event {
    data class UpdateOtp(val value: String): Event
    data object OnVerifyBtnClick : Event
    data object OnSendOtp: Event
}

interface AuthComponent: Events<Event> {
    val userEmail: String
    val optValue: StateFlow<String>
}


class DefaultAuthComponent(
    context: ComponentContext,
    userEmail: String
): ComponentContext by context, AuthComponent {

    override val userEmail: String = userEmail

    private val _otpValue = MutableStateFlow("")
    override val optValue: StateFlow<String> = _otpValue
    override fun onEvent(e: Event) {
        when(e) {
            is Event.UpdateOtp -> _otpValue.value = e.value
            Event.OnVerifyBtnClick -> TODO()
            Event.OnSendOtp -> TODO()
        }
    }
}

class FakeAuthComponent: AuthComponent {

    override val userEmail: String = "neerajshdev@gmail.com"

    private val _otpValue = MutableStateFlow("")
    override val optValue: StateFlow<String> = _otpValue
    override fun onEvent(e: Event) {
        when(e) {
            is Event.UpdateOtp -> _otpValue.value = e.value
            Event.OnVerifyBtnClick -> TODO()
            Event.OnSendOtp -> TODO()
        }
    }
}