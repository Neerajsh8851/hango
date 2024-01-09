package desidev.hango.ui.screens.signup_process.auth

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import desidev.hango.handler.OtpRequestHandle

interface AuthComponent {
    val userEmail: Value<String>
    val otpValue: Value<String>
    fun updateOtp(value: String)
    fun onSubmit()
    fun requestNewOtp()
}


class DefaultAuthComponent(
    context: ComponentContext,
    override val userEmail: Value<String>,
    private val onOtpEnter: OnOtpEnter,
    private val onSendAgain: OnSendAgain,
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

    override fun onSubmit() {
        onOtpEnter.onEnter(otpValue.value)
    }

    override fun requestNewOtp() = onSendAgain.onSendAgain()
}

class FakeAuthComponent : AuthComponent {
    override val userEmail: Value<String> = MutableValue("myname@example.com")
    private val _otpValue = MutableValue("45648")
    override val otpValue: Value<String> = _otpValue
    override fun updateOtp(value: String) {
    }

    override fun onSubmit() {
    }

    override fun requestNewOtp() {
    }
}