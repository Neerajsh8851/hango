package desidev.hango.ui.screens.signin

import kotlinx.coroutines.flow.StateFlow
import com.arkivanov.decompose.value.Value

interface SignInComponent {
    val userEmail: Value<String>
    val userPassword: Value<String>
    val hidePassword: Value<Boolean>

    fun updateEmail(email: String)
    fun updatePassword(password: String)
    fun togglePasswordVisibility()
    fun forgetPasswordClick()
    fun signUpClick()
    fun signInClick()
}


