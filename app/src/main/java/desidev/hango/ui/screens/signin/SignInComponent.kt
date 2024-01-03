package desidev.hango.ui.screens.signin

import com.arkivanov.decompose.ComponentContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

interface SignInComponent {
    val userEmail: StateFlow<String>
    val userPassword: StateFlow<String>
    val hidePassword: StateFlow<Boolean>

    fun updateEmail(email: String)
    fun updatePassword(password: String)
    fun togglePasswordVisibility()
    fun forgetPasswordClick()
    fun signUpClick()
    fun signInClick()
}


class DefaultSignInComponent(context: ComponentContext) : SignInComponent,
    ComponentContext by context {
    override val userEmail: StateFlow<String>
        get() = TODO("Not yet implemented")
    override val userPassword: StateFlow<String>
        get() = TODO("Not yet implemented")
    override val hidePassword: StateFlow<Boolean>
        get() = TODO("Not yet implemented")

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