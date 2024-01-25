package desidev.hango.ui.screens.signin

import com.arkivanov.decompose.ComponentContext
import kotlinx.coroutines.flow.StateFlow

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