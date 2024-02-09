package desidev.hango.ui.screens.signin

import android.util.Log
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import desidev.hango.api.AuthService
import desidev.hango.api.LoginError
import desidev.hango.api.model.UserCredential
import desidev.hango.ui.componentScope
import desidev.hango.ui.post
import desidev.kotlinutils.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

typealias OnSignupClick = () -> Unit
typealias OnForgetPasswordClick = () -> Unit
typealias OnUserSignedIn = () -> Unit

class DefaultSignInComponent(
    private val authService: AuthService,
    private val onUserSignIn: OnUserSignedIn,
    private val onSignupClick: OnSignupClick,
    private val onForgetPasswordClick: OnForgetPasswordClick,
    componentContext: ComponentContext
) : SignInComponent, ComponentContext by componentContext {

    companion object {
        private val TAG = DefaultSignInComponent::class.java.simpleName
    }

    data class Model(
        val userEmail: MutableValue<String>,
        val userPassword: MutableValue<String>,
        val hidePassword: MutableValue<Boolean>
    )

    private val scope = componentScope(Dispatchers.IO)

    private val model = Model(MutableValue(""), MutableValue(""), MutableValue(false))

    override val hidePassword: Value<Boolean> = model.hidePassword
    override val userEmail: Value<String> = model.userEmail
    override val userPassword: Value<String> = model.userPassword

    override fun updateEmail(email: String) {
        model.userEmail.post(email)
    }

    override fun updatePassword(password: String) {
        model.userPassword.post(password)
    }

    override fun togglePasswordVisibility() {
        model.hidePassword.post(!model.hidePassword.value)
    }

    override fun forgetPasswordClick() {
        onForgetPasswordClick()
    }

    override fun signUpClick() {
        onSignupClick()
    }

    override fun signInClick() {
        scope.launch {
            val loginResult = authService.login(
                UserCredential(
                    email = model.userEmail.value,
                    password = model.userPassword.value
                )
            )

            when (loginResult) {
                is Result.Ok -> {
                    onUserSignIn()
                    Log.d(TAG, "signInClick: user signed in successfully: ${loginResult.value}")
                }

                is Result.Err -> {
                    when (val error = loginResult.err) {
                        LoginError.InvalidCredential -> {
                            // TODO: show invalid credential message on screen
                            Log.d(TAG, "signInClick: invalid credential")
                        }

                        is LoginError.Error -> {
                            // TODO: show error message on screen
                            Log.d(TAG, "signInClick: failed with exception: ${error.exception} ")
                        }
                    }
                }
            }
        }
    }
}