package desidev.hango.ui.screens.signin

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


