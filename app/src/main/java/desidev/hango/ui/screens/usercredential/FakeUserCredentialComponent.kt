package desidev.hango.ui.screens.usercredential

import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value

class FakeUserCredentialComponent : UserCredentialComponent {
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

    override fun togglePasswordVisibility() {
        _hidePassword.value = hidePassword.value.not()
    }
    override fun onSubmit() {

    }

    override fun goBack() {

    }
}