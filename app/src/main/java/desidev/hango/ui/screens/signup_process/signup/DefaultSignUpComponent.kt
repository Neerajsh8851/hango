package desidev.hango.ui.screens.signup_process.signup

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import desidev.hango.handler.EmailUpdateHandle
import desidev.hango.handler.PasswordUpdateHandle

typealias OnSubmitClick = () -> Unit


class DefaultSignUpComponent(
    context: ComponentContext,
    override val userEmail: Value<String>,
    override val userPassword: Value<String>,
    private val onEmailUpdate: EmailUpdateHandle,
    private val onPasswordUpdate: PasswordUpdateHandle,
    private val onSubmitClick: OnSubmitClick,
) : SignUpComponent,
    ComponentContext by context {

    private val _confirmPassword = MutableValue(userPassword.value)
    override val confirmPassword: Value<String> = _confirmPassword
    private val _hidePassword = MutableValue(false)
    override val hidePassword: Value<Boolean> = _hidePassword

    override fun setEmail(email: String) {
        onEmailUpdate.updateEmail(email)
    }

    override fun setPassword(password: String) {
        onPasswordUpdate.updatePassword(password)
    }

    override fun setConfirmPassword(password: String) {
        _confirmPassword.value = password
    }

    override fun togglePasswordVisibility() {
        _hidePassword.value = hidePassword.value.not()
    }

    override fun onSubmit() {
        if (userPassword.value == confirmPassword.value) {
            onSubmitClick.invoke()
        }
    }
}