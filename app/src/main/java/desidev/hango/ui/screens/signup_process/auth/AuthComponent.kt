package desidev.hango.ui.screens.signup_process.auth

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import desidev.hango.handler.OtpRequestHandle
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

interface AuthComponent {
    val userEmail: Value<String>
    val optValue: StateFlow<String>
    fun updateOtp(value: String)
    fun verifyBtnClick()
    fun requestNewOtp()
}


class DefaultAuthComponent(
    context: ComponentContext,
    override val userEmail: Value<String>,
    private val otpSendRequestHandle: OtpRequestHandle,
): ComponentContext by context, AuthComponent {

    private val _otpValue = MutableStateFlow("")
    override val optValue: StateFlow<String> = _otpValue
    override fun updateOtp(value: String) {
        _otpValue.value = value
    }

    override fun verifyBtnClick() {
    }
    override fun requestNewOtp() = otpSendRequestHandle.sendOtpRequest()
}

class FakeAuthComponent: AuthComponent {
    override val userEmail: Value<String> = MutableValue("myname@example.com")
    private val _otpValue = MutableStateFlow("890889")
    override val optValue: StateFlow<String> = _otpValue
    override fun updateOtp(value: String) {
    }
    override fun verifyBtnClick() {
    }
    override fun requestNewOtp() {
    }
}