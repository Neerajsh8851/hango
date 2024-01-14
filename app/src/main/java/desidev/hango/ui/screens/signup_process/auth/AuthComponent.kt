package desidev.hango.ui.screens.signup_process.auth

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import desidev.hango.api.model.EmailAuthData
import desidev.kotlin.utils.Option

interface AuthComponent {
    val userEmail: Value<String>
    val otpValue: Value<String>
    val authData: Value<Option<EmailAuthData>>
    fun updateOtp(value: String)
    fun onOtpEnter()
    fun requestEmailAuth()
}


class DefaultAuthComponent(
    context: ComponentContext,
    override val userEmail: Value<String>,
    override val authData: Value<Option<EmailAuthData>>,
    private val onOtpEnter: OnOtpEnter,
    private val onRequestEmailAuth: OnSendAgain,
) : ComponentContext by context, AuthComponent {
    fun interface OnOtpEnter {
        fun onEnter(otpValue: String)
    }

    fun interface OnSendAgain {
        fun onSendAgain()
    }

    private val _otpValue = MutableValue("")
    override val otpValue: Value<String> = _otpValue
    override fun updateOtp(value: String) {
        _otpValue.value = value
    }

    override fun onOtpEnter() {
        onOtpEnter.onEnter(otpValue.value)
    }

    override fun requestEmailAuth() = onRequestEmailAuth.onSendAgain()
}

class FakeAuthComponent : AuthComponent {
    override val authData: Value<Option<EmailAuthData>> = MutableValue(Option.None)
    override val userEmail: Value<String> = MutableValue("myname@example.com")
    private val _otpValue = MutableValue("45648")
    override val otpValue: Value<String> = _otpValue
    override fun updateOtp(value: String) {

    }

    override fun onOtpEnter() {

    }

    override fun requestEmailAuth() {

    }
}