package desidev.hango.ui.screens.signup.signup

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import desidev.hango.handler.OnEmailValueUpdate
import desidev.hango.handler.OnPasswordValueUpdate
import desidev.hango.ui.screens.signup.profile.OnSubmit


class DefaultUserCredentialComponent(
    context: ComponentContext,
    override val userEmail: Value<String>,
    override val userPassword: Value<String>,
    private val onEmailUpdate: OnEmailValueUpdate,
    private val onPasswordUpdate: OnPasswordValueUpdate,
    private val onSubmitClick: OnSubmit,
) : UserCredentialComponent,
    ComponentContext by context {

    private val _confirmPassword = MutableValue(userPassword.value)
    override val confirmPassword: Value<String> = _confirmPassword
    private val _hidePassword = MutableValue(false)
    override val hidePassword: Value<Boolean> = _hidePassword

    override fun setEmail(email: String) {
        onEmailUpdate(email)
    }

    override fun setPassword(password: String) {
        onPasswordUpdate(password)
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