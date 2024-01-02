package desidev.hango.ui.screens.signup_process.signup

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import desidev.hango.ui.screens.Events
import desidev.onevent.OnEmailUpdate
import desidev.onevent.OnPasswordUpdate

sealed class Event {
    data class UpdateEmail(val value: String) : Event()
    data class UpdatePassword(val value: String) : Event()
    data class UpdateConfirmPassword(val value: String) : Event()
    data object SubmitClick : Event()
    data object ToggleHidePassword : Event()
}

interface SignUpComponent : Events<Event> {
    val userEmail: Value<String>
    val userPassword: Value<String>
    val hidePassword: Value<Boolean>
    val confirmPassword: Value<String>
}


fun interface OnSubmitClick {
    fun onSubmit()
}


class DefaultSignUpComponent(
    context: ComponentContext,
    override val userEmail: Value<String>,
    override val userPassword: Value<String>,
    private val onEmailUpdate: OnEmailUpdate,
    private val onPasswordUpdate: OnPasswordUpdate,
    private val onSubmitClick: OnSubmitClick,
) : SignUpComponent,
    ComponentContext by context {

    private val _confirmPassword = MutableValue("9890808")
    override val confirmPassword: Value<String> = _confirmPassword

    private val _hidePassword = MutableValue(false)
    override val hidePassword: Value<Boolean> = _hidePassword


    override fun sendEvent(e: Event) = when (e) {
        Event.SubmitClick -> onSubmitClick()
        Event.ToggleHidePassword -> _hidePassword.value = !_hidePassword.value
        is Event.UpdateEmail -> onEmailUpdate.updateEmail(e.value)
        is Event.UpdatePassword -> onPasswordUpdate.updatePassword(e.value)
        is Event.UpdateConfirmPassword -> _confirmPassword.value = e.value
    }

    private fun onSubmitClick() {
        if (userPassword == confirmPassword) {
            onSubmitClick.onSubmit()
        }
    }
}

class FakeSignUpComponent : SignUpComponent {
    private val _userEmail = MutableValue("neerajkaemail@meraemail.com")
    override val userEmail: Value<String> = _userEmail

    private val _userPassword = MutableValue("9890808")
    override val userPassword: Value<String> = _userPassword

    private val _hidePassword = MutableValue(false)
    override val hidePassword: Value<Boolean> = _hidePassword

    private val _confirmPassword = MutableValue("9890808")
    override val confirmPassword: Value<String> = _confirmPassword

    override fun sendEvent(e: Event) {
    }
}