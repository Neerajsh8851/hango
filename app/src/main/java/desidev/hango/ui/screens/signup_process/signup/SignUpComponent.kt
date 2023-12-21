package desidev.hango.ui.screens.signup_process.signup

import com.arkivanov.decompose.ComponentContext
import desidev.hango.ui.screens.Events
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

sealed class Event {
    data class UpdateEmail(val value: String): Event()
    data class UpdatePassword(val value: String): Event()
    data class UpdateConfirmPassword(val value: String): Event()
    data object SignUp: Event()
    data object ToggleHidePassword : Event()
}

interface SignUpComponent : Events<Event> {
    val userEmail: StateFlow<String>
    val userPassword: StateFlow<String>
    val hidePassword: StateFlow<Boolean>
    val confirmPassword: StateFlow<String>
}


class DefaultSignUpComponent(
    context: ComponentContext
) : SignUpComponent,
    ComponentContext by context {

    private val _userEmail = MutableStateFlow("neerajkaemail@meraemail.com")
    override val userEmail: StateFlow<String> = _userEmail

    private val _userPassword = MutableStateFlow("9890808")
    override val userPassword: StateFlow<String> = _userPassword

    private val _confirmPassword = MutableStateFlow("9890808")
    override val confirmPassword: StateFlow<String> = _confirmPassword

    private val _hidePassword = MutableStateFlow(false)
    override val hidePassword: StateFlow<Boolean> = _hidePassword


    override fun onEvent(event: Event) {
        when (event) {
            Event.SignUp -> registerNewAccount()
            Event.ToggleHidePassword -> _hidePassword.value = !_hidePassword.value
            is Event.UpdateEmail -> _userEmail.value = event.value
            is Event.UpdatePassword -> _userPassword.value = event.value
            is Event.UpdateConfirmPassword -> _confirmPassword.value = event.value
        }
    }
    private fun registerNewAccount() {
        if (userPassword != confirmPassword) return

    }
}

class FakeSignUpComponent : SignUpComponent {
    private val _userEmail = MutableStateFlow("neerajkaemail@meraemail.com")
    override val userEmail: StateFlow<String> = _userEmail

    private val _userPassword = MutableStateFlow("9890808")
    override val userPassword: StateFlow<String> = _userPassword

    private val _hidePassword = MutableStateFlow(false)
    override val hidePassword: StateFlow<Boolean> = _hidePassword

    private val _confirmPassword = MutableStateFlow("9890808")
    override val confirmPassword: StateFlow<String> = _confirmPassword

    override fun onEvent(event: Event) {
    }
}