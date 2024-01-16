package desidev.hango.ui.screens.signup_process.account

import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import desidev.hango.api.model.EmailAuthData

class FakeAccountComponent(
) : AccountComponent {
    override val userEmail: Value<String> = MutableValue("myname@example.com")
    private val _otpValue = MutableValue("45648")
    override val otpValue: Value<String> = _otpValue

    override val otpState: Value<AccountComponent.OTPState> =
        MutableValue(AccountComponent.OTPState.SendingOtp)

    override val accountCreateState: Value<AccountComponent.AccountCreateStatus> =
        MutableValue(AccountComponent.AccountCreateStatus.BeforeCreatingAccount)

    override fun requestEmailAuth() {
    }

    override fun setOtp(value: String) {
        _otpValue.value = value
    }

    override fun verifyOtp(authData: EmailAuthData) {
    }

    override fun createAccount(authData: EmailAuthData) {
    }
}