package desidev.hango.ui.screens.signup_process.signup

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import desidev.hango.handler.EmailUpdateHandle
import desidev.hango.handler.PasswordUpdateHandle


interface SignUpComponent  {
    val userEmail: Value<String>
    val userPassword: Value<String>
    val hidePassword: Value<Boolean>
    val confirmPassword: Value<String>

    fun setEmail(email: String)
    fun setPassword(password: String)
    fun setConfirmPassword(password: String)

    fun onSubmit()
}


fun interface OnSubmitClick {
    fun onSubmit()
}


class DefaultSignUpComponent(
    context: ComponentContext,
    override val userEmail: Value<String>,
    override val userPassword: Value<String>,
    private val onEmailUpdate: EmailUpdateHandle,
    private val onPasswordUpdate: PasswordUpdateHandle,
    private val onSubmitClick: OnSubmitClick,
) : SignUpComponent,
    ComponentContext by context {

    private val _confirmPassword = MutableValue("9890808")
    override val confirmPassword: Value<String> = _confirmPassword
    override fun setEmail(email: String) {
        onEmailUpdate.updateEmail(email)
    }

    override fun setPassword(password: String) {
        onPasswordUpdate.updatePassword(password)
    }

    override fun setConfirmPassword(password: String) {
        _confirmPassword.value = password
    }

    private val _hidePassword = MutableValue(false)
    override val hidePassword: Value<Boolean> = _hidePassword

    override fun onSubmit() {
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
    override fun setEmail(email: String) {
        _userEmail.value = email
    }

    override fun setPassword(password: String) {
        _userPassword.value = password
    }

    override fun setConfirmPassword(password: String) {
        _confirmPassword.value = password
    }

    override fun onSubmit() {

    }
}