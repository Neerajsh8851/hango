package desidev.hango.ui.screens.signin

import com.arkivanov.decompose.ComponentContext
import desidev.hango.ui.screens.Events
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

interface SignInComponent : Events<SignInComponent.Event> {
    val userEmail: StateFlow<String>
    val userPassword: StateFlow<String>
    val hidePassword: StateFlow<Boolean>

    sealed class Event {
        data class UpdateEmail(val value: String) : Event()
        data class UpdatePassword(val value: String) : Event()
        data object TogglePasswordVisibility : Event()
        data object ForgetPassClick : Event()
        data object SignUpClick : Event()
        data object SignInClick : Event()
    }
}


class DefaultSignInComponent(context: ComponentContext) : SignInComponent,
    ComponentContext by context {
    override val userEmail: StateFlow<String>
        get() = TODO("Not yet implemented")
    override val userPassword: StateFlow<String>
        get() = TODO("Not yet implemented")
    override val hidePassword: StateFlow<Boolean>
        get() = TODO("Not yet implemented")

    override fun sendEvent(e: SignInComponent.Event) {
    }
}


class FakeSignInComponent : SignInComponent {
    private val _userEmail = MutableStateFlow("neerajkaemail@meraemail.com")
    override val userEmail: StateFlow<String> = _userEmail

    private val _userPassword = MutableStateFlow("9890808")
    override val userPassword: StateFlow<String> = _userPassword

    private val _hidePassword = MutableStateFlow(false)
    override val hidePassword: StateFlow<Boolean> = _hidePassword

    override fun sendEvent(e: SignInComponent.Event) {
        when (e) {
            SignInComponent.Event.TogglePasswordVisibility -> {
                _hidePassword.value = hidePassword.value.not()
            }

            is SignInComponent.Event.UpdateEmail -> {
                _userEmail.value = e.value
            }

            is SignInComponent.Event.UpdatePassword -> {
                _userPassword.value = e.value
            }

            else -> {}
        }
    }
}