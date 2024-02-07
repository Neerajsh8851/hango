package desidev.hango.ui.screens.signin

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import desidev.hango.ui.post

typealias OnSignupClick = () -> Unit
typealias OnSigninClick = () -> Unit
typealias OnForgetPasswordClick = () -> Unit

class DefaultSignInComponent(
    componentContext: ComponentContext,
    private val onSignupClick: OnSignupClick,
    private val onSigninClick: OnSigninClick,
    private val onForgetPasswordClick: OnForgetPasswordClick
) :
    SigninComponent,
    ComponentContext by componentContext {
    data class Model(
        val userEmail: MutableValue<String>,
        val userPassword: MutableValue<String>,
        val hidePassword: MutableValue<Boolean>
    )

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
        onSigninClick
    }
}