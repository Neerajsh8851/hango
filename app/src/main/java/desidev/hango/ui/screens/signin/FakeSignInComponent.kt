package desidev.hango.ui.screens.signin

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class FakeSignInComponent : SignInComponent {
    private val _userEmail = MutableStateFlow("neerajkaemail@meraemail.com")
    override val userEmail: StateFlow<String> = _userEmail

    private val _userPassword = MutableStateFlow("9890808")
    override val userPassword: StateFlow<String> = _userPassword

    private val _hidePassword = MutableStateFlow(false)
    override val hidePassword: StateFlow<Boolean> = _hidePassword
    override fun updateEmail(email: String) {
        TODO("Not yet implemented")
    }

    override fun updatePassword(password: String) {
        TODO("Not yet implemented")
    }

    override fun togglePasswordVisibility() {
        TODO("Not yet implemented")
    }

    override fun forgetPasswordClick() {
        TODO("Not yet implemented")
    }

    override fun signUpClick() {
        TODO("Not yet implemented")
    }

    override fun signInClick() {
        TODO("Not yet implemented")
    }
}